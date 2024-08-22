package com.example.product.api

import androidx.room.Room
import com.example.product.internal.ProductRepositoryImpl
import com.example.product.internal.storage.ProductDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val DATABASE_NAME = "database-name"
private const val NAME_CONNECT_DATABASE = "database.db"

val productDataModule = module {
    singleOf(::ProductRepositoryImpl) bind ProductRepository::class
    single {
        Room.databaseBuilder(
            get(),
            ProductDatabase::class.java,
            DATABASE_NAME
        ).createFromAsset(NAME_CONNECT_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<ProductDatabase>().getProductDao() }
}