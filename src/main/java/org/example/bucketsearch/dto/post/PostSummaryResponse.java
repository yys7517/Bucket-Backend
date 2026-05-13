package org.example.bucketsearch.dto.post;

import org.example.bucketsearch.domain.post.Post;
import org.example.bucketsearch.dto.auth.AuthResponse;
import org.example.bucketsearch.dto.plan.PlanSummaryResponse;

import java.time.LocalDate;

public record PostSummaryResponse(
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
    public static PostSummaryResponse from(Post post) {

        return new PostSummaryResponse(
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
