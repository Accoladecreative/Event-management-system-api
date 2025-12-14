package com.challenge.app.category.dto

data class CategoryResponse(
    val id: Long,
    val label: String,
    val parentId: Long?
)

