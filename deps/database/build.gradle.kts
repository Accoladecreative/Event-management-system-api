plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

group = "com.challenge.deps.database"

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.postgresql:postgresql")
}