# Fetching latest version of Java
FROM openjdk:11

# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY ./target/newordle-0.0.1-SNAPSHOT.jar /app

# Exposing port 8080
# EXPOSE 8080

# Starting the application
ENTRYPOINT ["java", "-jar", "newordle-0.0.1-SNAPSHOT.jar"]
