FROM eclipse-temurin:21-jdk-alpine
WORKDIR /Hotel_ML_Auth_Service
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar
ENTRYPOINT ["java", "-jar","build/libs/Hotel_ML_Auth_Service-0.0.1.jar"]