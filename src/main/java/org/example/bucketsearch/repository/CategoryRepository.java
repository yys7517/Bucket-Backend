package org.example.bucketsearch.repository;

import org.example.bucketsearch.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
