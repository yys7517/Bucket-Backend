package org.example.bucketsearch.dto.post;

import org.example.bucketsearch.domain.Category;
import org.example.bucketsearch.domain.post.Post;
import org.example.bucketsearch.domain.PostLike;
import org.example.bucketsearch.dto.auth.AuthResponse;
import org.example.bucketsearch.dto.plan.PlanDetailResponse;

import java.time.LocalDate;
import java.util.List;

public record PostDetailResponse(
    Long id,
    String category,
    String categoryColor,
    String title,
    String memo,
    int likeCount,
//    boolean isLiked
//    boolean isMine, // TOOD. 유저
    LocalDate startDate,
    AuthResponse userInfo,
    List<PlanDetailResponse> plans
    // TODO. List<Comment> comments
) {
    public static PostDetailResponse from(Post post) {
        Category category = post.getCategory();
        List<PostLike> likes = post.getLikes();
        List<PlanDetailResponse> plans = post.getPlans().stream().map(PlanDetailResponse::from).toList();

        return new PostDetailResponse(
                post.getId(),
                category.getName(),
                category.getColor(),
                post.getTitle(),
                post.getMemo(),
                likes.size(),
                post.getStartDate(),
                AuthResponse.from(post.getUser()),
                plans
        );
    }
}
