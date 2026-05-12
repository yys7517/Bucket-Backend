package org.example.bucketsearch.dto.plan;

import org.example.bucketsearch.domain.Post;
import org.example.bucketsearch.domain.PostPlan;

import java.util.List;

public record PlanSummaryResponse(
        int totalCount,
        int completedCount,
        int progressRate
) {

    public static PlanSummaryResponse from(Post post) {
        List<PostPlan> postPlans = post.getPlans();
        List<PostPlan> completedPlans = postPlans.stream().filter(PostPlan::isCompleted).toList();

        int total = postPlans.size();
        int completed = completedPlans.size();
        int progress = total == 0 ? 0 : (completed * 100) / total;

        return new PlanSummaryResponse(
                total,
                completed,
                progress
        );
    }
}
