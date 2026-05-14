package org.example.bucketsearch.config;

import lombok.RequiredArgsConstructor;
import org.example.bucketsearch.domain.Category;
import org.example.bucketsearch.domain.PostLike;
import org.example.bucketsearch.domain.PostPlan;
import org.example.bucketsearch.domain.User;
import org.example.bucketsearch.domain.post.Post;
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
    private static final Long KAKAO_TEST_USER_ID = 4893725464L;
    private static final String KAKAO_TEST_USER_EMAIL = "yys7517@kakao.com";

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

            User kakaoTestUser = userRepository.findByKakaoId(KAKAO_TEST_USER_ID)
                    .or(() -> userRepository.findByEmail(KAKAO_TEST_USER_EMAIL))
                    .orElseGet(() -> userRepository.save(User.builder()
                            .kakaoId(KAKAO_TEST_USER_ID)
                            .email(KAKAO_TEST_USER_EMAIL)
                            .username("윤영선")
                            .profileImgUrl("http://k.kakaocdn.net/dn/buvx76/dJMcahjTIYA/YC0auDQSaszhLqsMVPhSc0/img_640x640.jpg")
                            .build()));

            Category travel = findOrCreateCategory("여행", "#3B82F6");
            Category learning = findOrCreateCategory("학습", "#10B981");
            Category health = findOrCreateCategory("건강", "#F97316");
            Category hobby = findOrCreateCategory("취미", "#A855F7");

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
            savePost(
                    "서울 야경 사진 스팟 정복하기",
                    "남산, 응봉산, 반포대교를 돌면서 야경 사진 포트폴리오 만들기",
                    hobby,
                    kakaoTestUser,
                    LocalDate.now().plusDays(5),
                    List.of("카메라 배터리 충전하기", "촬영 위치 저장하기", "보정 프리셋 만들기"),
                    1,
                    false
            );
            savePost(
                    "제주도 해안도로 자전거 여행",
                    "2박 3일 일정으로 제주 해안도로를 따라 라이딩하기",
                    travel,
                    kakaoTestUser,
                    LocalDate.now().plusDays(14),
                    List.of("자전거 대여 예약하기", "숙소 2곳 예약하기", "비상용품 챙기기", "코스 GPX 저장하기"),
                    2,
                    true
            );
            savePost(
                    "Redis Refresh Token 흐름 정리하기",
                    "로그인, 재발급, 로그아웃 플로우를 문서로 정리하고 API 테스트하기",
                    learning,
                    kakaoTestUser,
                    LocalDate.now().plusDays(2),
                    List.of("Redis 저장 키 확인하기", "refresh API 테스트하기", "logout 후 재발급 실패 확인하기"),
                    2,
                    true
            );
        });
    }

    private Category findOrCreateCategory(String name, String color) {
        return categoryRepository.findByName(name)
                .orElseGet(() -> categoryRepository.save(new Category(name, color)));
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
        if (postRepository.existsByTitleAndUserId(title, user.getId())) {
            return;
        }

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
