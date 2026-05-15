package org.example.bucketsearch.dto.plan;

import org.example.bucketsearch.domain.post.Post;
import org.example.bucketsearch.domain.SmallGoal;

import java.util.List;

public record PlanSummaryResponse(
        int totalCount,
        int completedCount,
        int progressRate
) {

    public static PlanSummaryResponse from(Post post) {
        List<SmallGoal> smallGoals = post.getSmallGoals();
        List<SmallGoal> completedPlans = smallGoals.stream().filter(SmallGoal::isCompleted).toList();

        int total = smallGoals.size();
        int completed = completedPlans.size();
        int progress = total == 0 ? 0 : (completed * 100) / total;

        return new PlanSummaryResponse(
                total,
                completed,
                progress
        );
    }
}
