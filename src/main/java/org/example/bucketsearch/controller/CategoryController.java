package org.example.bucketsearch.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.bucketsearch.dto.category.CategoryPostsResponse;
import org.example.bucketsearch.dto.common.BaseResponse;
import org.example.bucketsearch.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    // 카테고리 목록과 버킷리스트 수를 반환
    @Operation(summary = "카테고리 목록, 카테고리 별 버킷 리스트 수 조회")
    public ResponseEntity<BaseResponse<List<CategoryPostsResponse>>> findAllCategoryPosts() {
        return ResponseEntity.ok(BaseResponse.success(categoryRepository.findAll().stream()
                .map(CategoryPostsResponse::from)
                .toList()));
    }
}
