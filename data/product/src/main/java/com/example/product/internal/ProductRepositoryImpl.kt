package com.example.product.internal

import com.example.product.api.ProductRepository
import com.example.product.api.model.Product
import com.example.product.internal.mapper.toDataModel
import com.example.product.internal.storage.ProductDao
import kotlinx.coroutines.flow.Flow

internal class ProductRepositoryImpl(
    private val productDao: ProductDao,
) : ProductRepository {

    override val products: Flow<List<Product>>
        get() = TODO("Not yet implemented")

    override suspend fun getAllProducts(): List<Product> {
        return productDao.getAllItems().map { it.toDataModel() }
    }

    override suspend fun searchItems(query: String): List<Product> {
        return productDao.searchItems(query).map { it.toDataModel() }
    }

    override suspend fun updateItem(amount: Int, id: Int) {
        productDao.updateItem(amount = amount, id = id)
    }

    override suspend fun deleteItem(id: Int) {
        productDao.deleteItem(id)
    }
}