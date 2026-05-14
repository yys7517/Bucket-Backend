package org.example.bucketsearch.service.auth.token;

public interface TokenService {
    boolean matchesRefreshToken(Long userId, String refreshToken);
    void deleteRefreshToken(Long userId);
    void saveRefreshToken(Long userId, String refreshToken);
}
