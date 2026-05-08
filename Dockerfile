# --- 1단계: 빌드 ---
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY . .
# 윈도우 환경에서 작성된 파일일 경우를 대비해 줄바꿈 문자 처리(옵션) 후 권한 부여
RUN sed -i 's/\r$//' gradlew
RUN chmod +x ./gradlew
# 테스트 코드를 제외하고 빠르게 빌드
RUN ./gradlew clean build -x test

# --- 2단계: 실행 ---
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# 1단계에서 빌드된 jar 파일만 가져오기
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080