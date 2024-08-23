package com.example.product.api

import com.example.product.api.model.Product
import kotlinx.coroutines.flow.StateFlow

interface ProductRepository {

    val products: StateFlow<List<Product>>

    suspend fun getAllProduct()

    suspend fun searchProduct(query: String)

    suspend fun insertProduct(product: Product)

    suspend fun updateProductById(amount: Int, id: Int)

    suspend fun deleteProductById(id: Int)

}