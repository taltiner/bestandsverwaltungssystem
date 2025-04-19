FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -pl benachrichtigung-service -am -DskipTests

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /build/benachrichtigung-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]