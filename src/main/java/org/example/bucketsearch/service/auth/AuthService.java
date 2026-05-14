package org.example.bucketsearch.service.auth;

import org.example.bucketsearch.dto.auth.AuthResponse;
import org.example.bucketsearch.dto.auth.TokenRefreshResponse;

public interface AuthService {
    AuthResponse loginWithKakao(String accessToken);
    TokenRefreshResponse refreshAccessToken(String refreshToken);
    void logout(Long userId);
}
