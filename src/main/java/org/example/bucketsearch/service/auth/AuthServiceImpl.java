package org.example.bucketsearch.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bucketsearch.domain.User;
import org.example.bucketsearch.dto.auth.AuthResponse;
import org.example.bucketsearch.dto.auth.KakaoUserInfoResponse;
import org.example.bucketsearch.dto.auth.TokenRefreshResponse;
import org.example.bucketsearch.repository.UserRepository;
import org.example.bucketsearch.service.auth.token.JwtProvider;
import org.example.bucketsearch.service.auth.token.TokenService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider; // 자체 JWT 발급 클래스
    private final TokenService tokenService;
    private final RestTemplate restTemplate; // 외부 통신용

    @Override
    public AuthResponse loginWithKakao(String kakaoAccessToken) {
        // 1. 카카오 서버에서 유저 정보 가져오기
        KakaoUserInfoResponse kakaoUser = getKakaoUserInfo(kakaoAccessToken);

        // 2. DB에서 카카오 ID로 유저 조회, 없으면 즉시 회원가입(저장)
        User user = userRepository.findByKakaoId(kakaoUser.id())
                .orElseGet(() -> registerNewUser(kakaoUser));

        // 3. 우리 서버 전용 JWT (Access / Refresh) 발급
        String accessToken = jwtProvider.createAccessToken(user.getId());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());
        tokenService.saveRefreshToken(user.getId(), refreshToken);

        // 4. 모바일로 보낼 최종 응답 반환
        return new AuthResponse(user.getId(), accessToken, refreshToken);
    }

    @Override
    public TokenRefreshResponse refreshAccessToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 refresh token입니다.");
        }

        Long userId = jwtProvider.getUserId(refreshToken);
        if (!tokenService.matchesRefreshToken(userId, refreshToken)) {
            throw new RuntimeException("저장된 refresh token과 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.createAccessToken(userId);
        return new TokenRefreshResponse(accessToken);
    }

    @Override
    public void logout(Long userId) {
        tokenService.deleteRefreshToken(userId);    // 로그아웃 시, 리프레시 토큰 제거
    }

    private KakaoUserInfoResponse getKakaoUserInfo(String accessToken) {
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 💡 1. 결과를 우선 String으로 받아서 날것(Raw)의 JSON을 확인합니다.
        ResponseEntity<String> response = restTemplate.exchange(
                reqURL,
                HttpMethod.GET,
                entity,
                String.class
        );

        // ⭐ 카카오 서버에서 넘어온 실제 JSON 응답 콘솔 출력
        log.info("=== [카카오 API 응답 결과] ===");
        log.info("Status Code: {}", response.getStatusCode());
        log.info("Raw JSON Body: {}", response.getBody());
        log.info("==============================");

        // 💡 2. 확인한 JSON 문자열을 우리가 만든 DTO 객체로 직접 변환(매핑)해 줍니다.
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.getBody(), KakaoUserInfoResponse.class);
        } catch (JsonProcessingException e) {
            log.error("카카오 응답 JSON 파싱 오류", e);
            throw new RuntimeException("카카오 사용자 정보 매핑 실패", e);
        }
    }

    // --- [보조 로직] 신규 유저 DB 저장 ---
    private User registerNewUser(KakaoUserInfoResponse response) {
        Long kakaoId = response.id();
        KakaoUserInfoResponse.KakaoAccount account = response.kakao_account();
        KakaoUserInfoResponse.Profile profile = account == null ? null : account.profile();

        String nickname = profile == null || profile.nickname() == null
                ? "kakao-user-" + kakaoId
                : profile.nickname();
        String email = account == null ? null : account.email();
        String profileImg = profile == null ? null : profile.profile_image_url();

//       2. 로그로 확인해보기 (개발 단계에서 매우 유용!)
        log.info("새로운 유저 등록: 카카오 ID = {}, 닉네임={}, 이메일={}, 프로필={}",kakaoId, nickname, email, profileImg);

        User newUser = User.builder()
                .kakaoId(response.id())
                .username(nickname)
                .email(email)
                .profileImgUrl(profileImg)
                .build();

        return userRepository.save(newUser);
    }
}
