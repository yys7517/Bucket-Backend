package org.example.bucketsearch.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 소셜 로그인 식별자 (카카오 고유 ID) - 일반 가입 유저도 있을 수 있으니 null 허용
    @Column(unique = true)
    private Long kakaoId;

    private String email;

    private String username;

    private String password;

    @Column(unique = true)
    private String profileImgUrl;

    @OneToMany(mappedBy = "user")
    private final List<PostLike> likes = new ArrayList<>();
}
