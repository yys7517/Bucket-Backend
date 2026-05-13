package org.example.bucketsearch.service.home;

import org.example.bucketsearch.domain.post.Post;

import java.util.List;

public interface HomeService {
    List<Post> getPopularPosts();
    List<Post> getRecentPosts();
}
