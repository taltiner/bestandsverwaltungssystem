FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -pl bestand-service -am -DskipTests

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /build/bestand-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]