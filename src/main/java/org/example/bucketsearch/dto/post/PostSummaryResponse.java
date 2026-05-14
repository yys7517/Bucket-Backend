package org.example.bucketsearch.dto.post;

import org.example.bucketsearch.domain.post.Post;
import org.example.bucketsearch.dto.user.UserInfoResponse;
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
