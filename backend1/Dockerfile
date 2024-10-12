FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml ./

COPY src ./src

RUN ./mvnw package -DskipTests

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
