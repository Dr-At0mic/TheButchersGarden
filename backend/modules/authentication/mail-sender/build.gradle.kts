plugins {
	java
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "butchers-garden.main.auth.microservices"
version = "0.0.1-SNAPSHOT"

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
	mavenLocal()
}
extra["springCloudVersion"] = "2023.0.1"
dependencies {
	implementation("com.apputils:AppCommonUtils:0.0.1-SNAPSHOT")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	compileOnly("org.projectlombok:lombok")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}
tasks.withType<Test> {
	useJUnitPlatform()
}
