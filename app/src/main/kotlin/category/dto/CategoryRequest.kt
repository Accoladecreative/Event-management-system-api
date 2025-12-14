package com.challenge.app.category.dto

data class CategoryRequest(
    val label: String,
    val parentId: Long?
)

