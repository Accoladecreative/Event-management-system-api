plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

group = "com.challenge.app"

// Disable bootJar since main class is in root project
tasks.named("bootJar") {
    enabled = false
}

// Enable regular jar task
tasks.named("jar") {
    enabled = true
}

dependencies {
    implementation(project(":deps:database"))
    implementation(project(":deps:exception"))
    implementation(project(":deps:response"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
}