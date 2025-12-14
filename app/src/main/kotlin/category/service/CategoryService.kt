package com.challenge.app.category.service

import com.challenge.app.category.dto.CategoryRequest
import com.challenge.app.category.dto.CategoryResponse

interface CategoryService {
    fun addCategory(request: CategoryRequest): CategoryResponse
    fun removeCategory(id: Long)
    fun getSubtree(id: Long): List<CategoryResponse>
    fun moveSubtree(id: Long, newParentId: Long)

}