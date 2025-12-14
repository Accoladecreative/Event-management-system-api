package com.challenge

import com.challenge.dep.response.Response
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheck {

    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun health(): Response<String> {
        val data = "Welcome to Event Management System api"
        return Response(data = data)
    }
}