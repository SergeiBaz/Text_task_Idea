package com.example.test_task.feature.home.internal.components.dialog.adding_dislog

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.example.test_task.R

@Immutable
data class AddingProductDialogData(
    val title: String = "",
    @StringRes val primaryTextButton: Int? = R.string.confirm,
    @StringRes val secondaryTextButton: Int? = R.string.cancel,
)
