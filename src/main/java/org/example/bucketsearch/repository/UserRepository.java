package org.example.bucketsearch.repository;

import org.example.bucketsearch.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findFirstByEmailAndPassword(String email, String password);

    Optional<User> findByKakaoId(Long id);
}
