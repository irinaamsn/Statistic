plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	jacoco
}

group = "com.spring"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

jacoco {
	toolVersion = "0.8.9"
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	//rabbitmq
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	//jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	//jackson
	implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
	//security
	implementation("org.springframework.boot:spring-boot-starter-security")
	//boot
	implementation("org.springframework.boot:spring-boot-starter-web")
	//lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	//postgres
	runtimeOnly("org.postgresql:postgresql")
	//test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.amqp:spring-rabbit-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
	reports {
		xml.required = false
		csv.required = false
		html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
	}
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}
