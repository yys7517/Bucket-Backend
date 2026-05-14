package org.example.bucketsearch.dto.user;

import org.example.bucketsearch.domain.User;

public record UserInfoResponse(
        Long id,
        String email,
        String username,
        String profileImgUrl
) {
    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getProfileImgUrl()
        );
    }
}
