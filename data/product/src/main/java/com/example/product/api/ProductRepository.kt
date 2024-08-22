package com.example.product.api

import com.example.product.api.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    val products: Flow<List<Product>>

    suspend fun getAllProducts(): List<Product>

    suspend fun searchItems(query: String): List<Product>

    suspend fun updateItem(amount: Int, id: Int)

    suspend fun deleteItem(id: Int)

}