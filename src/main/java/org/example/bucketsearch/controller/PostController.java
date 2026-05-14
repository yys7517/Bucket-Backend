package org.example.bucketsearch.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.bucketsearch.domain.post.Post;
import org.example.bucketsearch.dto.common.BaseResponse;
import org.example.bucketsearch.dto.post.PostDetailResponse;
import org.example.bucketsearch.dto.user.CustomUserDetails;
import org.example.bucketsearch.service.post.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/{postId}")
    @Operation(summary = "버킷 게시글 상세")
    public ResponseEntity<BaseResponse<PostDetailResponse>> getPostDetail(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long currentUserId = userDetails.getUserId();

        Post found = postService.findById(postId);
        PostDetailResponse response = PostDetailResponse.from(found, currentUserId);

        return ResponseEntity.ok(
                BaseResponse.success(
                    response
                )
        );
    }

    // TODO. 내 프로필, 상대 프로필 게시글 목록 API
//    @GetMapping("/user/{userId}")
//    @Operation(summary = "유저가 작성한 게시글 목록")
//    public ResponseEntity<BaseResponse<PostSummaryResponse>> getPostSummary(
//            @PathVariable Long userId
//    ) {
//
//    }

    // TODO. 게시글 수정 API

    // TODO. 게시글 등록 API
}
