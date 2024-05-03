FROM eclipse-temurin:21-jdk-alpine
WORKDIR /Hotel_ML_Auth_Service
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar Hotel_ML_Auth_Service-0.0.1.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar","Hotel_ML_Auth_Service-0.0.1.jar"]