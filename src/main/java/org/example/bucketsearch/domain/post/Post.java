package org.example.bucketsearch.domain.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.bucketsearch.domain.Category;
import org.example.bucketsearch.domain.PostLike;
import org.example.bucketsearch.domain.SmallGoal;
import org.example.bucketsearch.domain.User;
import org.example.bucketsearch.domain.common.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Post extends BaseEntity {
    public static final int MAX_SMALL_GOAL_COUNT = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String goal;    // 큰 목표

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PostLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SmallGoal> smallGoals = new ArrayList<>();

    private LocalDate startDate;

    public void addSmallGoal(SmallGoal smallGoal) {
        if (smallGoals.size() >= MAX_SMALL_GOAL_COUNT) {
            throw new IllegalStateException("작은 목표는 최대 8개까지 등록할 수 있습니다.");
        }
        smallGoals.add(smallGoal);
        smallGoal.setPost(this);
    }
}
