/**
 * File: null.java
 * Path: com.example.pintbackend.domain
 * <p>
 * Outline:
 * post like entity to join with users and posts
 *
 * uniqueConstraint 포스트에 유저가 라이크 누를수있는 회수가 1.
 * <p>
 * Author: jskt
 */

package org.example.bucketsearch.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.bucketsearch.domain.post.Post;

@Entity
@Table(name = "post_likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "user_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static PostLike create(Post post, User user) {
        PostLike postLike = new PostLike();
        postLike.assignPost(post);
        postLike.assignUser(user);
        return postLike;
    }

    public void assignPost(Post post) {
        if (this.post != null) {
            this.post.getLikes().remove(this);
        }
        this.post = post;
        if (post != null && !post.getLikes().contains(this)) {
            post.getLikes().add(this);
        }
    }

    public void assignUser(User user) {
        if (this.user != null) {
            this.user.getLikes().remove(this);
        }
        this.user = user;
        if (user != null && !user.getLikes().contains(this)) {
            user.getLikes().add(this);
        }
    }
}
