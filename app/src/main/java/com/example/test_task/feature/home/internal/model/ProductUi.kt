package com.example.test_task.feature.home.internal.model

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class ProductUi(
    val id: Int = 0,
    val name: String? = null,
    val time: String? = null,
    val tags: PersistentList<String> = persistentListOf(),
    val amount: String? = null,
)
