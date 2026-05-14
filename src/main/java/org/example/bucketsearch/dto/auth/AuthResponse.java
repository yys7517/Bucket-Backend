package org.example.bucketsearch.dto.auth;

public record AuthResponse(
        Long userId,
        String accessToken,
        String refreshToken
) {
}
