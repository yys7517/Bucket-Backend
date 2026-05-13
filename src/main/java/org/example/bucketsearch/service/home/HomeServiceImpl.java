package org.example.bucketsearch.service.home;

import lombok.RequiredArgsConstructor;
import org.example.bucketsearch.domain.Post;
import org.example.bucketsearch.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final PostRepository postRepository;

    @Override
    public List<Post> getPopularPosts() {
        return postRepository.findPopularPosts();
    }

    @Override
    public List<Post> getRecentPosts() {
        return postRepository.findTop3ByOrderByCreatedAtDesc();
    }
}
