# ==========================================
# Stage 1: Build the application using Gradle
# ==========================================
FROM gradle:8.7.0-jdk21 AS build
WORKDIR /app

# Copy the gradle configuration files first to leverage Docker cache
COPY build.gradle settings.gradle ./
COPY src ./src

# Build the project and package it into an executable JAR, skipping unit tests for speed
RUN gradle clean bootJar -x test

# ==========================================
# Stage 2: Create the lightweight production image
# ==========================================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the packaged JAR file from the Gradle build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Explicitly restrict the JVM memory allocation to fit safely within the 512MB free tier limit
ENV JAVA_OPTS="-Xmx350m -Xms256m -XX:+UseSerialGC"

# Expose the internal port the application listens on
EXPOSE 8080

# Execute the application with memory flags
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]