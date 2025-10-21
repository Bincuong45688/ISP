
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
# copy jar từ stage build sang
COPY --from=build /app/target/*.jar app.jar

# Render sẽ map PORT runtime vào biến môi trường PORT
ENV PORT=8080
EXPOSE 8080

# chạy app
ENTRYPOINT ["java", "-jar", "app.jar"]
