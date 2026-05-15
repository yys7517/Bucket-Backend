package org.example.bucketsearch.dto.plan;

import org.example.bucketsearch.domain.SmallGoal;

import java.util.List;

public record SmallGoalResponse(
        Long id,
        int sortOrder,
        String color,
        String content,
        boolean isComplete,
        List<TodoDetailResponse> todos
//        boolean isMine, TOOD. 유저
) {
    public static SmallGoalResponse from(SmallGoal smallGoal) {
        return new SmallGoalResponse(
                smallGoal.getId(),
                smallGoal.getSortOrder(),
                smallGoal.getColor(),
                smallGoal.getContent(),
                smallGoal.isCompleted(),
                smallGoal.getTodos().stream()
                        .map(TodoDetailResponse::from)
                        .toList()
        );
    }
}
