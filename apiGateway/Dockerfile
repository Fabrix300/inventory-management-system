FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/apiGateway-0.0.1-SNAPSHOT.jar /app/apiGateway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/apiGateway.jar"]
