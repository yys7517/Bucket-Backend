package org.example.bucketsearch.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserInfoResponse(
    Long id,
    KakaoAccount kakao_account
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoAccount(
        Profile profile,
        String email
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Profile(
        String nickname,
        String profile_image_url
    ) {}
}
