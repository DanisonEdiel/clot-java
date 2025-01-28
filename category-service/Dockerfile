# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Final image
FROM amazoncorretto:17-alpine-jdk

COPY --from=build /target/category-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar","/app.jar" ]