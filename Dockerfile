FROM openjdk:22-jdk-slim-buster

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл внутрь контейнера
COPY target/demo-0.0.1-SNAPSHOT.jar /app/

# Указываем команду запуска
CMD ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]
