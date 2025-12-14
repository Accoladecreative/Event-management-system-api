package com.challenge.dep.database.postgres.config

import com.challenge.dep.database.postgres.model.PostgresModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PostgresConfiguration {
    
    @Bean
    fun postgresModel(
        @Value("\${spring.datasource.url}") url: String,
        @Value("\${spring.datasource.username}") username: String,
        @Value("\${spring.datasource.password}") password: String
    ): PostgresModel {
        return PostgresModel(
            url = url,
            name = username,
            password = password
        )
    }
}

