package com.challenge.app.category.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["com.challenge.app.category"])
@EntityScan(basePackages = ["com.challenge.app.category.model"])
@EnableJpaRepositories(basePackages = ["com.challenge.app.category.repository"])
class TestApplication

