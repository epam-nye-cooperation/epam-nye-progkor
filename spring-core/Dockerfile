FROM openjdk:17.0-jdk-slim-bullseye
COPY "./target/spring-core.jar" "/application/app.jar"
EXPOSE 8082
CMD ["java", "-jar", "/application/app.jar"]
