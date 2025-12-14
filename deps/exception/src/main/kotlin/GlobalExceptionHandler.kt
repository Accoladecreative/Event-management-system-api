package com.challenge.dep.exception

import com.challenge.dep.response.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException::class)
    fun handleNotFound(ex: CategoryNotFoundException): ResponseEntity<Response<String>> {
        val response = Response(
            status = false,
            data = ex.message ?: "Category not found"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }
}