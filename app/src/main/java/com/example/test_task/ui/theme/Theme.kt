package com.example.test_task.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    background = Background,
    surface = Background,
    surfaceTint = White
)

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    background = Background,
    surface = Background,
)

@Composable
fun Test_taskTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
