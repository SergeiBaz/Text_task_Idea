package com.example.product.internal.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
internal data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val time: Long,
    val tags: List<String>,
    val amount: Int
)
