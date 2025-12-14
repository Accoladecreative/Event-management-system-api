package com.challenge.app.category.controller

import com.challenge.app.category.dto.CategoryRequest
import com.challenge.app.category.dto.CategoryResponse
import com.challenge.app.category.service.CategoryService
import com.challenge.dep.response.Response
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val service: CategoryService
) {
    
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun add(@RequestBody request: CategoryRequest): Response<CategoryResponse> {
        val data = service.addCategory(request)
        return Response(data = data)
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun remove(@PathVariable id: Long): Response<String> {
        service.removeCategory(id)
        return Response(data = "Category deleted successfully")
    }
    
    @GetMapping("/{id}/subtree")
    fun subtree(@PathVariable id: Long): Response<List<CategoryResponse>> {
        val data = service.getSubtree(id)
        return Response(data = data)
    }
    
    @PutMapping("/{id}/move/{newParentId}")
    @ResponseStatus(HttpStatus.OK)
    fun move(
        @PathVariable id: Long,
        @PathVariable newParentId: Long
    ): Response<String> {
        service.moveSubtree(id, newParentId)
        return Response(data = "Subtree moved successfully")
    }
}

