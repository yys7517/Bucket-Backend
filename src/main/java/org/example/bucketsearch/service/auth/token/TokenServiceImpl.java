package org.example.bucketsearch.service.auth.token;

import lombok.RequiredArgsConstructor;
import org.example.bucketsearch.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    /** 리프레시 토큰 일치 여부 */
    @Override
    public boolean matchesRefreshToken(Long userId, String refreshToken) {
        return refreshTokenRepository.findByUserId(userId)
                .map(refreshToken::equals)
                .orElse(false);
    }

    /** 로그아웃 시, Refresh Token 제거 */
    @Override
    public void deleteRefreshToken(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    /** 유저 리프레시 토큰 저장 (리프레시 토큰 최초 발급 시) */
    @Override
    public void saveRefreshToken(Long userId, String refreshToken) {
        refreshTokenRepository.save(
                userId,
                refreshToken,
                Duration.ofMillis(jwtProvider.getRefreshTokenValidityMs())
        );
    }
}
