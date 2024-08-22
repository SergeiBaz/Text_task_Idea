package com.example.product.internal.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.product.api.model.Product
import com.example.product.internal.storage.entity.ProductEntity
import com.example.product.until.toDateString
import com.example.product.until.toMilliseconds

@RequiresApi(Build.VERSION_CODES.O)
internal fun ProductEntity.toDataModel(): Product {
    return Product(
        id = id,
        name = name,
        time = time.toDateString(),
        tags = tags,
        amount = amount,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
internal fun Product.toDatabaseModel(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        time = time.toMilliseconds(),
        tags = tags,
        amount = amount,
    )
}
