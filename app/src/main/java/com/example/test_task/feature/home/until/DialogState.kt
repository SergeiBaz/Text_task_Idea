package com.example.test_task.feature.home.until

/**
 * Закинул чисто для своего удобства
 * Должно быть в core.ui каком нибудь, но его было лень создавать
 */

sealed interface DialogState<out T> {

    data object Hidden : DialogState<Nothing>

    data class Visible<T>(val data: T) : DialogState<T>

}