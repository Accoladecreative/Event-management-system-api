package com.challenge.app.category.service

import com.challenge.app.category.dto.CategoryRequest
import com.challenge.app.category.model.CategoryEntity
import com.challenge.app.category.repository.CategoryRepository
import java.util.Optional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CategoryServiceTest {

    @Mock
    lateinit var repository: CategoryRepository

    lateinit var service: CategoryServiceImpl

    @BeforeEach
    fun setUp() {
        service = CategoryServiceImpl(repository)
    }

    @Test
    fun `should add root category`() {
        val request = CategoryRequest("Root", null)
        val entity = CategoryEntity(id = 1, label = "Root", parent = null)

        `when`(repository.save(any())).thenReturn(entity)

        val response = service.addCategory(request)

        assertEquals(1, response.id)
        assertEquals("Root", response.label)
        assertNull(response.parentId)
    }

    @Test
    fun `should add child category`() {
        val parent = CategoryEntity(id = 1, label = "Root", parent = null)
        val child = CategoryEntity(id = 2, label = "Child", parent = parent)

        `when`(repository.findById(1)).thenReturn(Optional.of(parent))
        `when`(repository.save(any())).thenReturn(child)

        val response = service.addCategory(CategoryRequest("Child", 1))

        assertEquals(2, response.id)
        assertEquals("Child", response.label)
        assertEquals(1, response.parentId)
    }

    @Test
    fun `should delete category`() {
        val root = CategoryEntity(id = 1, label = "Root", parent = null)
        `when`(repository.findById(1)).thenReturn(Optional.of(root))
        doNothing().`when`(repository).deleteById(1)

        service.removeCategory(1)

        verify(repository, times(1)).deleteById(1)
    }

    @Test
    fun `should fetch subtree recursively`() {
        val root = CategoryEntity(id = 1, label = "Root", parent = null)
        val child1 = CategoryEntity(id = 2, label = "Child1", parent = root)
        val child2 = CategoryEntity(id = 3, label = "Child2", parent = root)
        val grandchild = CategoryEntity(id = 4, label = "Grandchild", parent = child1)

        `when`(repository.findById(1)).thenReturn(Optional.of(root))
        `when`(repository.fetchSubtree(1)).thenReturn(listOf(root, child1, child2, grandchild))

        val result = service.getSubtree(1)

        assertEquals(4, result.size)
        assertTrue(result.any { it.id.toInt() == 1 })
        assertTrue(result.any { it.id.toInt() == 2 })
        assertTrue(result.any { it.id.toInt() == 3 })
        assertTrue(result.any { it.id.toInt() == 4 })
    }

    @Test
    fun `should move subtree to new parent`() {
        val root1 = CategoryEntity(id = 1, label = "Root1", parent = null)
        val root2 = CategoryEntity(id = 2, label = "Root2", parent = null)
        val child = CategoryEntity(id = 3, label = "Child", parent = root1)

        `when`(repository.findById(3)).thenReturn(Optional.of(child))
        `when`(repository.findById(2)).thenReturn(Optional.of(root2))

        service.moveSubtree(3, 2)

    }
}

