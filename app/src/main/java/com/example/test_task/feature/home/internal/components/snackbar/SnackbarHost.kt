package com.example.test_task.feature.home.internal.components.snackbar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.test_task.ui.theme.RedPrimary

private const val DURATION_ANIMATION = 500

@Composable
internal fun SnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    containerColor: Color = RedPrimary,
    contentColor: Color = White
) {

    SnackbarHost(
        hostState = hostState,
        modifier = modifier
    ) { snackbarData: SnackbarData ->
        CustomSnackbar(
            shape = RectangleShape,
            snackbarData = snackbarData,
            textAlign = TextAlign.Center,
            containerColor = containerColor,
            contentColor = contentColor,
            modifier = modifier
                .animateContentSize(
                    animationSpec =
                    TweenSpec(durationMillis = DURATION_ANIMATION)
                )
                .heightIn(min = 55.dp)
        )
    }
}

@Composable
private fun CustomSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    shape: Shape = SnackbarDefaults.shape,
    containerColor: Color = SnackbarDefaults.color,
    contentColor: Color = SnackbarDefaults.contentColor,
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor, shape)
            .padding(contentPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = snackbarData.visuals.message,
                textAlign = textAlign,
                color = contentColor,
                modifier = Modifier.weight(1f)
            )
        }
    }
}