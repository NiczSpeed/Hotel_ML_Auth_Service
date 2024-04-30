plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.ml"
version = "0.0.1"

val mapstructVersion = "1.5.5.Final"
val junitJupiterVersion = "5.10.2"
val instancioVersion = "4.4.0"
val jwtVersion = "4.4.0"
val jsonVersion = "20240303"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

tasks.jar {
	manifest {
		archiveFileName.set("${project.name}-${project.version}.jar")
	}
}

tasks.test {
	useJUnitPlatform()
}


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
//	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.apache.kafka:kafka-streams")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.mapstruct:mapstruct:${mapstructVersion}")
	implementation("com.auth0:java-jwt:${jwtVersion}")
	implementation("org.json:json:${jsonVersion}")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api:${junitJupiterVersion}")
	testImplementation("org.instancio:instancio-junit:${instancioVersion}")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitJupiterVersion}")
	annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
