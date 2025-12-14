package com.challenge

import com.challenge.app.category.config.CategoryConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import


@SpringBootApplication(
    scanBasePackages = [
        "com.challenge.app",
        "com.challenge",
        "com.challenge.dep.database",
        "com.challenge.dep.exception",
    ]
)
@Import(
    CategoryConfig::class,
)
class ChallengeApplication

fun main(args: Array<String>) {
    runApplication<ChallengeApplication>(*args)
}
