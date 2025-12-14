package com.challenge.app.category.repository

import com.challenge.app.category.model.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CategoryRepository : JpaRepository<CategoryEntity, Long> {
    
    @Query(
        value = """
        WITH RECURSIVE subtree(id, label, parent_id) AS (
            SELECT id, label, parent_id FROM categories WHERE id = :root
            UNION ALL
            SELECT c.id, c.label, c.parent_id FROM categories c, subtree s 
            WHERE c.parent_id = s.id
        )
        SELECT id, label, parent_id FROM subtree
        """,
        nativeQuery = true
    )
    fun fetchSubtree(@Param("root") root: Long): List<CategoryEntity>
    
    fun findByParentId(parentId: Long?): List<CategoryEntity>
}