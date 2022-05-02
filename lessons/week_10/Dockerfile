FROM openjdk:11

WORKDIR app
COPY target/hellosvc.jar /app/hellosvc.jar

EXPOSE 8080
ENTRYPOINT java -jar /app/hellosvc.jar
