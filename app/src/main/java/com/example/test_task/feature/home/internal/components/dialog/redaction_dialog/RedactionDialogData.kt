package com.example.test_task.feature.home.internal.components.dialog.redaction_dialog

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.example.test_task.R

@Immutable
data class RedactionDialogData(
    val primaryText: String = "",
    val quantity: Int = 0,
    val id: Int? = null,
    @StringRes val primaryTextButton: Int? = R.string.confirm,
    @StringRes val secondaryTextButton: Int? = R.string.cancel,
)
