package org.example.bucketsearch.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bucketsearch.dto.auth.AuthRequest;
import org.example.bucketsearch.dto.auth.AuthResponse;
import org.example.bucketsearch.dto.auth.TokenRefreshResponse;
import org.example.bucketsearch.dto.common.BaseResponse;
import org.example.bucketsearch.service.auth.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "회원 인증 API")
public class AuthController {

    private static final String BEARER_PREFIX = "Bearer ";
    private final AuthService authService;

    @PostMapping("/kakao")
    public ResponseEntity<BaseResponse<AuthResponse>> kakaoLogin(@RequestBody AuthRequest authRequest) {
        AuthResponse response = authService.loginWithKakao(authRequest.accessToken());

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<TokenRefreshResponse>> refreshAccessToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    ) {
        String refreshToken = resolveBearerToken(authorization);
        TokenRefreshResponse response = authService.refreshAccessToken(refreshToken);

        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        authService.logout(userId);

        return ResponseEntity.ok(BaseResponse.success(null));
    }

    private String resolveBearerToken(String authorization) {
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            throw new RuntimeException("Bearer 토큰이 필요합니다.");
        }
        return authorization.substring(BEARER_PREFIX.length());
    }
}
