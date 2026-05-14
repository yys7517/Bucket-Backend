package org.example.bucketsearch.service.user;

import lombok.RequiredArgsConstructor;
import org.example.bucketsearch.domain.User;
import org.example.bucketsearch.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findFirstByEmailAndPassword(email, password)
                .orElse(null);
    }
}
