package org.example.bucketsearch.service.auth;

import org.example.bucketsearch.dto.auth.AuthResponse;

public interface AuthService {
    AuthResponse loginWithKakao(String accessToken);
}
