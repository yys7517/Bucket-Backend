package org.example.bucketsearch.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.bucketsearch.domain.post.Post;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "small_goals",
        uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "sort_order"}))
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmallGoal {
    public static final int MAX_TODO_COUNT = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private int sortOrder;

    private String color;

    @Column(nullable = false)
    private String content;

    private boolean isCompleted;

    @OneToMany(mappedBy = "smallGoal", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Todo> todos = new ArrayList<>();

    public void addTodo(Todo todo) {
        if (todos.size() >= MAX_TODO_COUNT) {
            throw new IllegalStateException("할 일은 작은 목표마다 최대 8개까지 등록할 수 있습니다.");
        }
        todos.add(todo);
        todo.setSmallGoal(this);
    }
}
