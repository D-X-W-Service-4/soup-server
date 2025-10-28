# Dockerfile

# jdk21 Image Start
FROM openjdk:21

# jar 파일 복제
COPY build/libs/*.jar app.jar

# 실행 명령어
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]