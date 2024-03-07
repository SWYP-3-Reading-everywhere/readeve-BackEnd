FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*.jar
ARG PROFILES
ARG ENV
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${PROFILES} -Dserver.env=${ENV} -jar app.jar > log.out 2>&1"]