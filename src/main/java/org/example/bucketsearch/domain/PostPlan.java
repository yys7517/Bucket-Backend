package org.example.bucketsearch.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.bucketsearch.domain.post.Post;

@Entity
@Table(name = "plans")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private int sortOrder;

    private String content;

    private boolean isCompleted;
}
