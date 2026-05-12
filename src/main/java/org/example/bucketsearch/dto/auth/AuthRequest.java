package org.example.bucketsearch.dto.auth;

public record AuthRequest(
        String email,
        String password
) {
}
