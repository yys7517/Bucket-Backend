package org.example.bucketsearch.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "todos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"small_goal_id", "sort_order"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "small_goal_id", nullable = false)
    private SmallGoal smallGoal;

    private int sortOrder;

    private String color;

    @Column(nullable = false)
    private String content;

    private boolean isCompleted;
}
