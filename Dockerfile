# Use a base image with OpenJDK 17
FROM openjdk:17-jdk-slim as build

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the src directory to the container
COPY pom.xml .
COPY src ./src

# Run Maven to build the application (skip tests to speed up the build)
RUN mvn clean package -DskipTests

# Use a smaller OpenJDK runtime image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/restaurantservice-0.0.1-SNAPSHOT.jar restaurantservice.jar

# Expose the port that your Spring Boot application will run on (default 8080)
EXPOSE 8082

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "orderservice.jar"]