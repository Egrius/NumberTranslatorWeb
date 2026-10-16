FROM openjdk:22-jdk-slim-buster

WORKDIR /app

COPY target/demo-0.0.1-SNAPSHOT.jar /app/

CMD ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]
