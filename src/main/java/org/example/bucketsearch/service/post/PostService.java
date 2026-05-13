package org.example.bucketsearch.service.post;

import org.example.bucketsearch.domain.post.Post;

public interface PostService {
    Post findById(Long id);
}
