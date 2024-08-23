package com.example.product.di

import android.content.Context
import androidx.room.Room
import com.example.product.api.ProductRepository
import com.example.product.internal.ProductRepositoryImpl
import com.example.product.internal.storage.ProductDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val DATABASE_NAME = "database-name"
private const val NAME_CONNECT_DATABASE = "data.db"

val productDataModule = module {
    singleOf(::ProductRepositoryImpl) bind ProductRepository::class
    single {
        val context = get<Context>()
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        val builder = Room.databaseBuilder(
            get(),
            ProductDatabase::class.java,
            DATABASE_NAME
        )
        if (!dbFile.exists()) {
            builder.createFromAsset(NAME_CONNECT_DATABASE)
        }
        builder.fallbackToDestructiveMigration().build()
    }
    single { get<ProductDatabase>().getProductDao() }
}