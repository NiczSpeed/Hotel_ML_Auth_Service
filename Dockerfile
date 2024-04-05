FROM eclipse-temurin:21-jdk-alpine
WORKDIR /hotel_ml_auth_service
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar Hotel_ML_Auth_Service-0.0.1.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar","Hotel_ML_Auth_Service-0.0.1.jar"]