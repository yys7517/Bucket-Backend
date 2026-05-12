package org.example.bucketsearch.dto.auth;

import org.example.bucketsearch.domain.User;

public record AuthResponse(
        Long id,
        String email,
        String username,
        String profileImgUrl
) {
    public static AuthResponse from(User user) {
        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getProfileImgUrl()
        );
    }
}
