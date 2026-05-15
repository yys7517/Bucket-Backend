package org.example.bucketsearch.dto.post;

import org.example.bucketsearch.domain.Category;
import org.example.bucketsearch.domain.PostLike;
import org.example.bucketsearch.domain.post.Post;
import org.example.bucketsearch.dto.plan.SmallGoalResponse;

import java.time.LocalDate;
import java.util.List;

public record PostDetailResponse(
    Long id,
    String category,
    String categoryColor,
    String goal,
    String memo,
    int likeCount,
    boolean isLiked,
    boolean isMine,
    LocalDate startDate,
    List<SmallGoalResponse> smallGoals
    // TODO. List<Comment> comments
) {
    public static PostDetailResponse from(Post post, Long userId) {
        Category category = post.getCategory();
        List<PostLike> likes = post.getLikes();
        List<SmallGoalResponse> smallGoals = post.getSmallGoals().stream().map(SmallGoalResponse::from).toList();

        boolean isMine = post.getUser().getId().equals(userId);
        boolean isLiked = post.getLikes().stream()
                .anyMatch(postLike -> postLike.getUser().getId().equals(userId));

        return new PostDetailResponse(
                post.getId(),
                category.getName(),
                category.getColor(),
                post.getGoal(),
                post.getMemo(),
                likes.size(),
                isLiked,
                isMine,
                post.getStartDate(),
                smallGoals
        );
    }
}
