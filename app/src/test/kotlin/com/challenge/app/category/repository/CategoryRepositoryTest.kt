package com.challenge.app.category.repository

import com.challenge.app.category.config.TestApplication
import com.challenge.app.category.model.CategoryEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = [TestApplication::class])
@ActiveProfiles("test")
class CategoryRepositoryTest {
    
    @Autowired
    private lateinit var repository: CategoryRepository
    
    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }
    
    @Test
    fun `should save and find category`() {
        val category = CategoryEntity(
            label = "Test Category",
            parent = null
        )
        
        val saved = repository.save(category)
        
        assertNotNull(saved.id)
        assertTrue(repository.existsById(saved.id))
        assertEquals("Test Category", saved.label)
    }
    
    @Test
    fun `should save category with parent`() {
        val parent = repository.save(CategoryEntity(label = "Parent", parent = null))
        val child = repository.save(CategoryEntity(label = "Child", parent = parent))
        
        assertNotNull(child.id)
        assertEquals(parent.id, child.parent?.id)
    }
    
    @Test
    fun `should fetch subtree with single node`() {
        val root = repository.save(CategoryEntity(label = "Root", parent = null))
        
        val subtree = repository.fetchSubtree(root.id)
        
        assertEquals(1, subtree.size)
        assertEquals(root.id, subtree[0].id)
        assertEquals("Root", subtree[0].label)
    }
    
    @Test
    fun `should fetch subtree with multiple levels`() {
        val root = repository.save(CategoryEntity(label = "Root", parent = null))
        val child1 = repository.save(CategoryEntity(label = "Child1", parent = root))
        val child2 = repository.save(CategoryEntity(label = "Child2", parent = root))
        val grandchild = repository.save(CategoryEntity(label = "Grandchild", parent = child1))
        
        val subtree = repository.fetchSubtree(root.id)
        
        assertEquals(4, subtree.size)
        assertTrue(subtree.any { it.id == root.id && it.label == "Root" })
        assertTrue(subtree.any { it.id == child1.id && it.label == "Child1" })
        assertTrue(subtree.any { it.id == child2.id && it.label == "Child2" })
        assertTrue(subtree.any { it.id == grandchild.id && it.label == "Grandchild" })
    }
    
    @Test
    fun `should fetch subtree starting from middle node`() {
        val root = repository.save(CategoryEntity(label = "Root", parent = null))
        val child1 = repository.save(CategoryEntity(label = "Child1", parent = root))
        val child2 = repository.save(CategoryEntity(label = "Child2", parent = root))
        val grandchild1 = repository.save(CategoryEntity(label = "Grandchild1", parent = child1))
        val grandchild2 = repository.save(CategoryEntity(label = "Grandchild2", parent = child1))
        
        val subtree = repository.fetchSubtree(child1.id)
        
        assertEquals(3, subtree.size)
        assertTrue(subtree.any { it.id == child1.id })
        assertTrue(subtree.any { it.id == grandchild1.id })
        assertTrue(subtree.any { it.id == grandchild2.id })
        assertFalse(subtree.any { it.id == root.id })
        assertFalse(subtree.any { it.id == child2.id })
    }
    
    @Test
    fun `should delete category`() {
        val category = repository.save(CategoryEntity(label = "ToDelete", parent = null))
        val id = category.id
        
        repository.deleteById(id)
        
        assertFalse(repository.existsById(id))
    }
    
    @Test
    fun `should find all categories`() {
        repository.save(CategoryEntity(label = "Category1", parent = null))
        repository.save(CategoryEntity(label = "Category2", parent = null))
        repository.save(CategoryEntity(label = "Category3", parent = null))
        
        val all = repository.findAll()
        
        assertEquals(3, all.size)
    }
}

