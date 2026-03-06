# Multi-stage build for Kotlin/Gradle application
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy gradle files
COPY gradlew gradlew.bat gradle.properties ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./

# Copy source code
COPY src ./src

# Build the application
RUN chmod +x gradlew && ./gradlew build -x test

# Runtime stage
FROM eclipse-temurin:21-jre-alpine-3.23

WORKDIR /app

# Copy the built classes and resources from builder stage
COPY --from=builder /app/build/classes/kotlin/main ./classes
COPY --from=builder /app/src/main/resources ./resources

# Set environment variables
ENV APP_HOME=/app

# Create a non-root user for running the app
RUN addgroup -g 1000 appuser && \
    adduser -D -u 1000 -G appuser appuser && \
    chown -R appuser:appuser /app

USER appuser

# Expose the web server port
EXPOSE 8080

# Run the application
CMD ["java", "-cp", "/app/classes:/app/resources", "org.example.MainKt"]
