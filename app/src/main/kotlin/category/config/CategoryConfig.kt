package com.challenge.app.category.config

import com.challenge.app.category.repository.CategoryRepository
import com.challenge.app.category.service.CategoryService
import com.challenge.app.category.service.CategoryServiceImpl
import com.challenge.dep.database.postgres.config.PostgresConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["com.challenge.app.category.model"])
@EnableJpaRepositories(basePackages = ["com.challenge.app.category.repository"])
@Import(
    PostgresConfiguration::class
)
class CategoryConfig {

    @Bean
    fun categoryService(
        categoryRepository: CategoryRepository
    ): CategoryService {
        return CategoryServiceImpl(
            repository = categoryRepository
        )
    }
}

