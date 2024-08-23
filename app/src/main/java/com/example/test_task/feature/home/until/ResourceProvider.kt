package com.example.test_task.feature.home.until

import android.content.Context
import androidx.annotation.StringRes

/**
 * Закинул чисто для своего удобства
 * Должно быть в core каком нибудь, но его было лень создавать
 */

interface ResourceProvider {
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String
}

internal class ResourceProviderImpl(private val context: Context) : ResourceProvider {

    override fun getString(resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }
}