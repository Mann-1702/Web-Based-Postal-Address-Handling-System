FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY target/address-manager-1.0.0.jar app.jar

# Expose the application's port
EXPOSE 9091

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=local"]
