package org.example.bucketsearch.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.bucketsearch.dto.user.CustomUserDetails;
import org.example.bucketsearch.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetails loadUserById(Long userId) {
        return userRepository.findById(userId)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
