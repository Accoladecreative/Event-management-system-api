package com.challenge.dep.response

data class Response<T>(
    val status: Boolean = true,
    val data: T
)