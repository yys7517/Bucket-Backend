package org.example.bucketsearch.dto.plan;

import org.example.bucketsearch.domain.Todo;

public record TodoDetailResponse(
        Long id,
        int sortOrder,
        String color,
        String content,
        boolean isComplete
) {
    public static TodoDetailResponse from(Todo todo) {
        return new TodoDetailResponse(
                todo.getId(),
                todo.getSortOrder(),
                todo.getColor(),
                todo.getContent(),
                todo.isCompleted()
        );
    }
}
