FROM gradle:jdk21 AS build
WORKDIR /Hotel_ML_Auth_Service
COPY . .
RUN chmod +x gradlew
RUN gradle clean bootJar

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /Hotel_ML_Auth_Service
COPY --from=build /Hotel_ML_Auth_Service/build/libs/*.jar Hotel_ML_Auth_Service.jar
ENTRYPOINT ["java", "-jar","Hotel_ML_Auth_Service.jar"]