package org.example.bucketsearch.config;

import lombok.RequiredArgsConstructor;
import org.example.bucketsearch.domain.Category;
import org.example.bucketsearch.domain.Post;
import org.example.bucketsearch.domain.PostLike;
import org.example.bucketsearch.domain.PostPlan;
import org.example.bucketsearch.domain.User;
import org.example.bucketsearch.repository.CategoryRepository;
import org.example.bucketsearch.repository.PostRepository;
import org.example.bucketsearch.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TestUserInitializer {

    private static final String TEST_USER_EMAIL = "test@example.com";

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final TransactionTemplate transactionTemplate;

    @Bean
    CommandLineRunner createDummyData() {
        return args -> transactionTemplate.executeWithoutResult(status -> {
            User testUser = userRepository.findByEmail(TEST_USER_EMAIL)
                    .orElseGet(() -> userRepository.save(User.builder()
                            .email(TEST_USER_EMAIL)
                            .username("test-user")
                            .password("test1234")
                            .profileImgUrl("https://example.com/test-user.png")
                            .build()));

            if (postRepository.count() > 0) {
                return;
            }

            Category travel = categoryRepository.save(new Category("여행", "#3B82F6"));
            Category learning = categoryRepository.save(new Category("학습", "#10B981"));
            Category health = categoryRepository.save(new Category("건강", "#F97316"));

            savePost(
                    "한라산 백록담 등반하기",
                    "올해 안에 날씨 좋은 날을 골라 한라산 정상까지 올라가기",
                    travel,
                    testUser,
                    LocalDate.now().plusDays(10),
                    List.of("항공권 예약하기", "등산화 점검하기", "성판악 코스 예약하기"),
                    2,
                    true
            );
            savePost(
                    "Spring Boot로 MVP API 완성하기",
                    "홈, 인증, 게시글 조회까지 모바일에서 붙일 수 있는 수준으로 정리하기",
                    learning,
                    testUser,
                    LocalDate.now().plusDays(3),
                    List.of("홈 응답 DTO 만들기", "더미 데이터 넣기", "Swagger로 응답 확인하기"),
                    1,
                    true
            );
            savePost(
                    "매주 3회 러닝 루틴 만들기",
                    "무리하지 않고 꾸준히 뛰는 습관 만들기",
                    health,
                    testUser,
                    LocalDate.now().plusDays(1),
                    List.of("러닝화 준비하기", "첫 3km 뛰기", "주간 기록 남기기"),
                    0,
                    false
            );
            savePost(
                    "부산 해운대 일출 보기",
                    "새벽 기차로 내려가서 해운대에서 일출 보고 오기",
                    travel,
                    testUser,
                    LocalDate.now().plusDays(20),
                    List.of("기차표 예매하기", "근처 맛집 저장하기"),
                    1,
                    false
            );
        });
    }

    private void savePost(
            String title,
            String memo,
            Category category,
            User user,
            LocalDate startDate,
            List<String> planContents,
            int completedPlanCount,
            boolean liked
    ) {
        Post post = Post.builder()
                .title(title)
                .memo(memo)
                .category(category)
                .user(user)
                .startDate(startDate)
                .build();

        for (int index = 0; index < planContents.size(); index++) {
            post.getPlans().add(PostPlan.builder()
                    .post(post)
                    .sortOrder(index + 1)
                    .content(planContents.get(index))
                    .isCompleted(index < completedPlanCount)
                    .build());
        }

        if (liked) {
            PostLike.create(post, user);
        }

        postRepository.save(post);
    }
}
