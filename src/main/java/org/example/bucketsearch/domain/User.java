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

    private String email;

    private String username;

    private String password;

    @Column(unique = true)
    private String profileImgUrl;

    @OneToMany(mappedBy = "user")
    private final List<PostLike> likes = new ArrayList<>();
}
