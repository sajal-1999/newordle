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

# FROM adoptopenjdk/openjdk11:ubi as build

# WORKDIR /app

# COPY mvnw .
# COPY .mvn .mvn

# COPY pom.xml .

# # Building dependency so it can work without internet access
# RUN ./mvnw dependency:go-offline -B

# COPY src src

# RUN ./mvnw package -DskipTests
# RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


# FROM adoptopenjdk/openjdk11:ubi

# ARG DEPENDENCY=/app/target/dependency

# # COPY --from=build ${DEPENDENCY}/BOOT_INF/lib /app/lib
# # COPY --from=build ${DEPENDENCY}/META_INF /app/META_INF
# # COPY --from=build ${DEPENDENCY}/BOOT_INF/classes /app

# ENTRYPOINT ["java","-cp","app:app/lib/*","com/newordle/newordle"]
