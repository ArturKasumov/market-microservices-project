# Use a Java runtime as the base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /storage-ms-app

# Copy the application JAR file
COPY target/storage-ms-0.0.1-SNAPSHOT.jar storage-ms-app.jar

# Expose the port your app runs on (e.g., 8080)
EXPOSE 1010

# Command to run the application
ENTRYPOINT ["java", "-jar", "storage-ms-app.jar"]