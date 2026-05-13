package org.example.bucketsearch.global;

import lombok.extern.slf4j.Slf4j;
import org.example.bucketsearch.domain.post.exception.PostNotFoundException;
import org.example.bucketsearch.dto.common.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 게시글 조회 실패
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handlePostNotFound(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }
}
