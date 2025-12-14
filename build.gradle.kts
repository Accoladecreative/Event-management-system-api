plugins {
	id("org.springframework.boot") version "3.1.5" apply false
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23" apply false
	kotlin("plugin.jpa") version "1.9.23" apply false
}

group = "com.challenge"
version = "0.0.1-SNAPSHOT"
description = "Category Event Management System Code challenge - Kolade Oluwadare"

repositories {
	mavenCentral()
}

apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "kotlin")
apply(plugin = "kotlin-spring")
apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

dependencies {
	implementation(project(":app"))
	implementation(project(":deps:database"))
	implementation(project(":deps:response"))
	implementation(project(":deps:exception"))
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	mainClass.set("com.challenge.ChallengeApplicationKt")
}

subprojects {
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-spring")
	
	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(17)
		}
	}

	repositories {
		mavenCentral()
	}

	// Disable bootJar for all subprojects (they are library modules)
	tasks.named("bootJar") {
		enabled = false
	}

	// Enable regular jar task
	tasks.named("jar") {
		enabled = true
	}

	kotlin {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
