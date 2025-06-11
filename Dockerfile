# Multi-stage build for Spring Boot application
FROM maven:3.9-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Install Node.js (required for frontend build)
RUN apt-get update && \
    apt-get install -y nodejs npm && \
    apt-get clean

# Copy pom.xml and download dependencies
COPY . .
RUN mvn clean install

# Copy source code
COPY src ./src
COPY package.json package-lock.json* ./

# Build the application (compilation, npm install, npm run build)
RUN mvn package -DskipTests

# Expose the application port (Spring Boot default is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["mvn", "spring-boot:run"]
