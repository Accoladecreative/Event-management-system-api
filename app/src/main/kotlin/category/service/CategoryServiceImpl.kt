package com.challenge.app.category.service

import com.challenge.app.category.dto.CategoryRequest
import com.challenge.app.category.dto.CategoryResponse
import com.challenge.dep.exception.CategoryNotFoundException
import com.challenge.app.category.model.CategoryEntity
import com.challenge.app.category.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
class CategoryServiceImpl(
    private val repository: CategoryRepository
) : CategoryService {

   override fun addCategory(request: CategoryRequest): CategoryResponse {
        val parent = request.parentId?.let {
            repository.findByIdOrThrow(it)
        }

        val category = CategoryEntity(
            label = request.label,
            parent = parent
        )

        val saved = repository.save(category)
        return saved.toResponse()
    }

   override fun removeCategory(id: Long) {
        repository.findByIdOrThrow(id)
        repository.deleteById(id)
    }

    @Transactional(readOnly = true)
   override fun getSubtree(id: Long): List<CategoryResponse> {
        repository.findByIdOrThrow(id)
        return try {
            repository.fetchSubtree(id).map { it.toResponse() }
        } catch (e: Exception) {
            // Fallback to Java-based recursion for databases
            // that don't support recursive CTEs
            fetchSubtreeRecursive(id).map { it.toResponse() }
        }
    }
    
    private fun fetchSubtreeRecursive(rootId: Long): List<CategoryEntity> {
        val result = mutableListOf<CategoryEntity>()
        val root = repository.findById(rootId).orElse(null) ?: return result
        result.add(root)
        
        val queue = mutableListOf(root)
        while (queue.isNotEmpty()) {
            val current = queue.removeAt(0)
            val children = repository.findByParentId(current.id)
            result.addAll(children)
            queue.addAll(children)
        }
        
        return result
    }

   override fun moveSubtree(id: Long, newParentId: Long) {
        val subtreeRoot = repository.findByIdOrThrow(id)
        val newParent = repository.findByIdOrThrow(newParentId)

        subtreeRoot.parent = newParent
        repository.save(subtreeRoot)
    }
}

private fun CategoryRepository.findByIdOrThrow(id: Long): CategoryEntity {
    return findById(id).orElseThrow { CategoryNotFoundException(id) }
}

private fun CategoryEntity.toResponse(): CategoryResponse {
    return CategoryResponse(
        id = id,
        label = label,
        parentId = parent?.id
    )
}


