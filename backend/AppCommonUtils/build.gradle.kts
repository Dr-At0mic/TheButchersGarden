plugins {
	java
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("maven-publish")
}

group = "com.apputils"
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
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	testImplementation("io.projectreactor:reactor-test")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
			versionMapping {
				usage("java-api") {
					fromResolutionResult()
				}
				usage("java-runtime") {
					fromResolutionResult()
				}
			}
		}
	}
	repositories {
		maven {
			name = "local"
			url = uri(layout.buildDirectory.dir("repo"))
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}