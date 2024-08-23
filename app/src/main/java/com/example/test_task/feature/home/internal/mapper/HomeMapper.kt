package com.example.test_task.feature.home.internal.mapper

import com.example.product.api.model.Product
import com.example.test_task.feature.home.internal.model.ProductUi
import kotlinx.collections.immutable.toPersistentList

internal fun Product.toUiModel(): ProductUi {
    return ProductUi(
        id = id,
        name = name,
        time = time,
        tags = tags.toPersistentList(),
        amount = if (amount > 0) amount.toString() else null,
    )
}

internal fun ProductUi.toDataModel(): Product {
    return Product(
        id = id,
        name = name.orEmpty(),
        time = time.orEmpty(),
        tags = tags,
        amount = amount?.toIntOrNull() ?: 0,
    )
}