package com.challenge.dep.exception

class CategoryNotFoundException(id: Long) : RuntimeException("Category with id $id not found.")