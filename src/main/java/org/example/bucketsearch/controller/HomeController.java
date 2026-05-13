package org.example.bucketsearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bucketsearch.dto.common.BaseResponse;
import org.example.bucketsearch.dto.post.PostSummaryResponse;
import org.example.bucketsearch.service.home.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/popular")
    public ResponseEntity<BaseResponse<List<PostSummaryResponse>>> getPopularPosts() {
        return ResponseEntity.ok(BaseResponse.success(
                homeService.getPopularPosts().stream()
                .map(PostSummaryResponse::from)
                .toList()));
    }

    @GetMapping("/recent")
    public ResponseEntity<BaseResponse<List<PostSummaryResponse>>> getTopPosts() {
        return ResponseEntity.ok(BaseResponse.success(
                homeService.getRecentPosts().stream()
                        .map(PostSummaryResponse::from)
                        .toList()
        ));
    }
}
