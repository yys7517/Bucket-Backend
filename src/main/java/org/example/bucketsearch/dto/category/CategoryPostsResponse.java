package org.example.bucketsearch.dto.category;

import org.example.bucketsearch.domain.Category;

public record CategoryPostsResponse(
        Long id,
        String name,
        String color,
        int count
) {
    public static CategoryPostsResponse from(Category category) {
        int postCounts = category.getPosts().size();

        return new CategoryPostsResponse(
                category.getId(),
                category.getName(),
                category.getColor(),
                postCounts
        );
    }
}
