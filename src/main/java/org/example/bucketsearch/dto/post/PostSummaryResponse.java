package org.example.bucketsearch.dto.post;

import org.example.bucketsearch.domain.post.Post;
import org.example.bucketsearch.dto.plan.PlanSummaryResponse;
import org.example.bucketsearch.dto.user.UserInfoResponse;

import java.time.LocalDate;

public record PostSummaryResponse(
        Long id,
        String category,
        String categoryColor,
        String title,
        int likeCount,
        LocalDate startDate,
        UserInfoResponse userInfo,
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
                UserInfoResponse.from(post.getUser()),
                PlanSummaryResponse.from(post)
        );
    }
}
