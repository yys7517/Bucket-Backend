package org.example.bucketsearch.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.bucketsearch.domain.post.Post;
import org.example.bucketsearch.dto.common.BaseResponse;
import org.example.bucketsearch.dto.post.PostDetailResponse;
import org.example.bucketsearch.dto.post.PostSummaryResponse;
import org.example.bucketsearch.service.post.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/{postId}")
    @Operation(summary = "버킷 게시글 상세")
    public ResponseEntity<BaseResponse<PostDetailResponse>> getPostDetail(@PathVariable Long postId) {
        Post found = postService.findById(postId);
        PostDetailResponse response = PostDetailResponse.from(found);

        return ResponseEntity.ok(
                BaseResponse.success(
                    response
                )
        );
    }
}
