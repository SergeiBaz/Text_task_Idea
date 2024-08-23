package com.example.test_task.feature.home.di

import com.example.test_task.feature.home.internal.screen.home_screen.HomeViewModel
import com.example.test_task.feature.home.until.ResourceProvider
import com.example.test_task.feature.home.until.ResourceProviderImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeFeatureModule = module {
    viewModelOf(::HomeViewModel)
    singleOf(::ResourceProviderImpl) bind ResourceProvider::class
}