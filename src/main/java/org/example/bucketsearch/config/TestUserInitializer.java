package org.example.bucketsearch.config;

import lombok.RequiredArgsConstructor;
import org.example.bucketsearch.domain.Category;
import org.example.bucketsearch.domain.PostLike;
import org.example.bucketsearch.domain.SmallGoal;
import org.example.bucketsearch.domain.Todo;
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
    private static final List<String> GOAL_COLORS = List.of(
            "#A982D9", "#EF6A7A", "#F49AB0", "#F5A261",
            "#5FC47D", "#6F92E6", "#50BFC4", "#7E62E8"
    );

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
                    "대기업 입사",
                    "백엔드 개발자로 대기업 신입 공채에 최종 합격하기",
                    learning,
                    testUser,
                    LocalDate.now().plusDays(60),
                    List.of(
                            smallGoal("서류 준비", "이력서 초안 작성", "프로젝트 경험 정리", "자기소개서 문항 분석", "지원 회사 리스트업"),
                            smallGoal("코딩 테스트 준비", "알고리즘 유형별 문제 풀이", "SQL 문제 풀이", "주간 모의 테스트", "오답 노트 정리"),
                            smallGoal("면접 준비", "CS 면접 스터디", "프로젝트 면접 스터디", "모의 면접", "AI 면접 준비")
                    ),
                    2,
                    true
            );
            savePost(
                    "개인 서비스 출시",
                    "직접 만든 서비스를 배포하고 실제 사용자 피드백까지 받아보기",
                    learning,
                    testUser,
                    LocalDate.now().plusDays(45),
                    List.of(
                            smallGoal("MVP 기능 완성", "인증 API 구현", "게시글 상세 API 구현", "좋아요 기능 구현", "예외 응답 정리"),
                            smallGoal("배포 환경 구성", "Dockerfile 점검", "EC2 환경변수 설정", "도메인 연결", "배포 스크립트 테스트"),
                            smallGoal("사용자 피드백 수집", "테스트 유저 모집", "피드백 폼 작성", "핵심 개선사항 정리")
                    ),
                    1,
                    true
            );
            savePost(
                    "하프 마라톤 완주",
                    "부상 없이 21km를 완주할 수 있는 체력 만들기",
                    health,
                    testUser,
                    LocalDate.now().plusDays(90),
                    List.of(
                            smallGoal("기초 체력 만들기", "주 3회 3km 러닝", "러닝 후 스트레칭", "수면 시간 기록", "심박수 체크"),
                            smallGoal("거리 늘리기", "5km 완주", "10km 완주", "페이스 조절 연습", "장거리 러닝 루틴 만들기"),
                            smallGoal("대회 준비", "대회 신청", "러닝 장비 점검", "전날 식단 준비", "완주 전략 세우기")
                    ),
                    0,
                    false
            );
            savePost(
                    "제주 한 달 살기",
                    "제주에서 한 달 동안 일과 여행을 병행하는 생활 경험하기",
                    travel,
                    testUser,
                    LocalDate.now().plusDays(120),
                    List.of(
                            smallGoal("숙소 준비", "지역 후보 비교", "월세 예산 계산", "숙소 예약", "인터넷 환경 확인"),
                            smallGoal("일정 설계", "평일 업무 루틴 작성", "주말 여행지 정리", "맛집 지도 저장", "비상 일정 마련"),
                            smallGoal("생활 준비", "짐 리스트 작성", "교통수단 예약", "생활비 예산 세우기")
                    ),
                    1,
                    false
            );
            savePost(
                    "사진 포트폴리오 만들기",
                    "서울 야경과 인물 사진을 모아 포트폴리오 사이트 완성하기",
                    hobby,
                    kakaoTestUser,
                    LocalDate.now().plusDays(30),
                    List.of(
                            smallGoal("촬영 콘셉트 정하기", "레퍼런스 이미지 수집", "촬영 무드보드 만들기", "필요 장비 체크"),
                            smallGoal("야경 촬영", "남산 촬영", "응봉산 촬영", "반포대교 촬영", "촬영본 백업"),
                            smallGoal("포트폴리오 정리", "대표 사진 선별", "보정 프리셋 적용", "포트폴리오 페이지 업로드")
                    ),
                    1,
                    false
            );
            savePost(
                    "자전거 국토종주",
                    "안전하게 코스를 완주하고 완주 인증까지 받기",
                    travel,
                    kakaoTestUser,
                    LocalDate.now().plusDays(75),
                    List.of(
                            smallGoal("체력 준비", "주말 30km 라이딩", "업힐 연습", "장거리 후 회복 루틴", "라이딩 기록 점검"),
                            smallGoal("코스 준비", "구간별 거리 계산", "숙소 예약", "보급 지점 표시", "비상 연락처 정리"),
                            smallGoal("장비 준비", "타이어 점검", "예비 튜브 구매", "헬멧 상태 확인", "방수팩 준비")
                    ),
                    2,
                    true
            );
            savePost(
                    "백엔드 인증 구조 완성",
                    "JWT와 Redis를 활용해 로그인, 재발급, 로그아웃 흐름을 안정화하기",
                    learning,
                    kakaoTestUser,
                    LocalDate.now().plusDays(14),
                    List.of(
                            smallGoal("JWT 인증 연결", "SecurityConfig 점검", "JWT 필터 동작 확인", "인증 실패 응답 확인"),
                            smallGoal("Refresh Token 관리", "Redis 저장 키 확인", "refresh API 테스트", "만료 시간 검증", "재발급 응답 확인"),
                            smallGoal("로그아웃 검증", "logout API 호출", "Redis 토큰 삭제 확인", "삭제 후 refresh 실패 확인")
                    ),
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
            String goal,
            String memo,
            Category category,
            User user,
            LocalDate startDate,
            List<SmallGoalSeed> smallGoalSeeds,
            int completedSmallGoalCount,
            boolean liked
    ) {
        if (postRepository.existsByGoalAndUserId(goal, user.getId())) {
            return;
        }

        Post post = Post.builder()
                .goal(goal)
                .memo(memo)
                .category(category)
                .user(user)
                .startDate(startDate)
                .build();

        for (int index = 0; index < smallGoalSeeds.size(); index++) {
            SmallGoalSeed seed = smallGoalSeeds.get(index);
            SmallGoal smallGoal = SmallGoal.builder()
                    .sortOrder(index + 1)
                    .color(GOAL_COLORS.get(index % GOAL_COLORS.size()))
                    .content(seed.content())
                    .isCompleted(index < completedSmallGoalCount)
                    .build();

            addSampleTodos(smallGoal, seed.todos(), index < completedSmallGoalCount);
            post.addSmallGoal(smallGoal);
        }

        if (liked) {
            PostLike.create(post, user);
        }

        postRepository.save(post);
    }

    private SmallGoalSeed smallGoal(String content, String... todos) {
        return new SmallGoalSeed(content, List.of(todos));
    }

    private void addSampleTodos(SmallGoal smallGoal, List<String> todoContents, boolean completed) {
        for (int index = 0; index < todoContents.size(); index++) {
            smallGoal.addTodo(Todo.builder()
                    .sortOrder(index + 1)
                    .color(smallGoal.getColor())
                    .content(todoContents.get(index))
                    .isCompleted(completed && index == 0)
                    .build());
        }
    }

    private record SmallGoalSeed(
            String content,
            List<String> todos
    ) {
    }
}
