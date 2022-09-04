FROM openjdk:11-jre-slim
ADD target/spring-mongo-docker.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]