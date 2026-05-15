package org.example.bucketsearch.repository;

import org.example.bucketsearch.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            select p
            from Post p
            order by size(p.likes) desc
            """)
    List<Post> findPopularPosts();

    List<Post> findTop3ByOrderByCreatedAtDesc();

    boolean existsByGoalAndUserId(String goal, Long userId);
}
