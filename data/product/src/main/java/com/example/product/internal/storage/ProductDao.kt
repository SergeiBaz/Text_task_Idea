package com.example.product.internal.storage

import androidx.room.Dao
import androidx.room.Query
import com.example.product.internal.storage.entity.ProductEntity

@Dao
internal interface ProductDao {

    @Query("SELECT * FROM item")
    suspend fun getAllItems(): List<ProductEntity>

    @Query("SELECT * FROM item WHERE name LIKE :query")
    suspend fun searchItems(query: String): List<ProductEntity>

    @Query("UPDATE item SET amount = :amount WHERE id = :id")
    suspend fun updateItem(amount: Int, id: Int)

    @Query("DELETE FROM item WHERE id = :id")
    suspend fun deleteItem(id: Int)
}