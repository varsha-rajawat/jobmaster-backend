# Use an official Maven image to build the app
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Package the app
RUN mvn clean package -DskipTests

# Use a lightweight Java runtime for the final image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the packaged jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app will run on
EXPOSE 10000

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
