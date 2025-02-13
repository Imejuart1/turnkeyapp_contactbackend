# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim AS builder

# Set working directory inside the container
WORKDIR /app

# Copy Gradle files first (to optimize build cache)
COPY gradlew gradlew.bat gradle/ ./
COPY build.gradle settings.gradle ./
COPY src ./src

# Grant execution permission for Gradle Wrapper
RUN chmod +x gradlew

# Build the application (creates the JAR file)
RUN ./gradlew clean build -x test

# Production stage: Use a smaller JDK image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
