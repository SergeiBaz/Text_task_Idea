package com.example.product.internal.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.product.internal.storage.ProductDatabase.Companion.DB_VERSION
import com.example.product.internal.storage.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = DB_VERSION,
)
@TypeConverters(Converters::class)
internal abstract class ProductDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    companion object {
        const val DB_VERSION = 1
    }
}
