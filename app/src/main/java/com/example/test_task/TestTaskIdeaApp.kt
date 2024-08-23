package com.example.test_task

import android.app.Application
import com.example.product.di.productDataModule
import com.example.test_task.di.appModule
import com.example.test_task.feature.home.di.homeFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class TestTaskIdeaApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                productDataModule,
                appModule,
                homeFeatureModule,
            )
        }
    }
}