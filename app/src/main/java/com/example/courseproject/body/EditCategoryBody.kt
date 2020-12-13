package com.example.courseproject.body

class EditCategoryBody (
    private val id: Int,
    internal val title: String,
    internal val price: Int,
    internal val purchaseRequirement: Boolean
)

