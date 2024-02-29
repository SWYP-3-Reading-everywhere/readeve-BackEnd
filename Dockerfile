FROM openjdk:17-jdk-slim
ADD build/libs/*.jar /app/
ENTRYPOINT ["java","-jar","/app/your-app.jar"]


#ADD /build/libs/*.jar app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
