package org.example.bucketsearch.dto.post;

import org.example.bucketsearch.domain.Post;
import org.example.bucketsearch.dto.auth.AuthResponse;
import org.example.bucketsearch.dto.plan.PlanSummaryResponse;

import java.time.LocalDate;

public record PostResponseDto(
        Long id,
        String category,
        String categoryColor,
        String title,
        int likeCount,
//        boolean isLiked, TODO. USER 필요
        LocalDate startDate,
        AuthResponse userInfo,
        PlanSummaryResponse planSummary
) {
    public static PostResponseDto from(Post post) {

        return new PostResponseDto(
                post.getId(),
                post.getCategory().getName(),
                post.getCategory().getColor(),
                post.getTitle(),
                post.getLikes().size(),
                post.getStartDate(),
                AuthResponse.from(post.getUser()),
                PlanSummaryResponse.from(post)
        );
    }
}
