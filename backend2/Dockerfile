FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/backend2-0.0.1-SNAPSHOT.jar /app/backend2.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/backend2.jar"]
