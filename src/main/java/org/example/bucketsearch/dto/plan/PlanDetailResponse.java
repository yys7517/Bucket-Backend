package org.example.bucketsearch.dto.plan;

import org.example.bucketsearch.domain.PostPlan;

public record PlanDetailResponse(
        Long id,
        int sortOrder,
        String content,
        boolean isComplete
//        boolean isMine, TOOD. 유저
) {
    public static PlanDetailResponse from(PostPlan postPlan) {
        return new PlanDetailResponse(
                postPlan.getId(),
                postPlan.getSortOrder(),
                postPlan.getContent(),
                postPlan.isCompleted()
        );
    }
}
