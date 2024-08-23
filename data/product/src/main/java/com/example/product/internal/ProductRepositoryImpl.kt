package com.example.product.internal

import com.example.product.api.ProductRepository
import com.example.product.api.model.Product
import com.example.product.internal.mapper.toDataModel
import com.example.product.internal.mapper.toDatabaseModel
import com.example.product.internal.storage.ProductDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class ProductRepositoryImpl(
    private val productDao: ProductDao,
) : ProductRepository {
    private val _productListStateFlow: MutableStateFlow<List<Product>> =
        MutableStateFlow(emptyList())
    override val products: StateFlow<List<Product>> =
        _productListStateFlow.asStateFlow()

    override suspend fun getAllProduct() {
        _productListStateFlow.update {
            productDao.getAllItems().map { it.toDataModel() }
        }
    }

    override suspend fun searchProduct(query: String) {
        _productListStateFlow.update {
            productDao.searchItems(query).map { it.toDataModel() }
        }
    }

    override suspend fun insertProduct(product: Product) {
        productDao.insertItem(item = product.toDatabaseModel())
        getAllProduct()
    }

    override suspend fun updateProductById(amount: Int, id: Int) {
        productDao.updateItem(amount = amount, id = id)
        getAllProduct()
    }

    override suspend fun deleteProductById(id: Int) {
        productDao.deleteItem(id)
        getAllProduct()
    }
}