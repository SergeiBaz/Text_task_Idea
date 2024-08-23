package com.example.product.internal.mapper

import com.example.product.api.model.Product
import com.example.product.internal.storage.entity.ProductEntity
import com.example.product.until.toDateString
import com.example.product.until.toMilliseconds

internal fun ProductEntity.toDataModel(): Product {
    return Product(
        id = id,
        name = name,
        time = time.toDateString(),
        tags = tags,
        amount = amount,
    )
}

internal fun Product.toDatabaseModel(): ProductEntity {
    return ProductEntity(
        name = name,
        time = time.toMilliseconds(),
        tags = tags,
        amount = amount,
    )
}
