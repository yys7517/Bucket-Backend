package org.example.bucketsearch.service.post;

import lombok.RequiredArgsConstructor;
import org.example.bucketsearch.domain.post.Post;
import org.example.bucketsearch.domain.post.exception.PostNotFoundException;
import org.example.bucketsearch.repository.CategoryRepository;
import org.example.bucketsearch.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException(id)
        );
    }
}
