package com.example.test_task.feature.home.internal.components.swipe

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.test_task.R
import com.example.test_task.ui.theme.BluePrimary
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@SuppressLint("SuspiciousIndentation")
@Composable
fun <T> SwipeableLazyColumn(
    modifier: Modifier = Modifier,
    listItems: List<T>,
    onSwipeStartToEnd: (T) -> Unit = {},
    onSwipeEndToStart: (T) -> Unit = {},
    color: Color = Color.Transparent,
    lazyListState: LazyListState = rememberLazyListState(),
    enableSwipeToStart: Boolean = false,
    enableSwipeToEnd: Boolean = false,
    swipeToStartThreshold: Float = 0f,
    swipeToEndThreshold: Float = 0f,
    @DrawableRes startIcon: Int = R.drawable.ic_sharp_edit,
    @DrawableRes endIcon: Int = R.drawable.ic_trash_can,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable (T, Int) -> Unit
) {
    LazyColumn(
        state = lazyListState,
        contentPadding = contentPadding,
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(listItems) { index, item ->
            var boxSize by remember { mutableFloatStateOf(0F) }
            val scope = rememberCoroutineScope()
            var offsetX by remember { mutableFloatStateOf(0f) }

            val maxOffsetX = boxSize * swipeToEndThreshold
            val minOffsetX = -boxSize * swipeToStartThreshold
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        boxSize = coordinates.size.width.toFloat()
                    }
                    .background(color)
            ) {
                if (enableSwipeToEnd) {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterStart)
                            .fillMaxWidth(swipeToEndThreshold)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = startIcon),
                            tint = BluePrimary,
                            contentDescription = "startIcon",
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .clickable {
                                    scope.launch {
                                        offsetX = 0f
                                        onSwipeStartToEnd(item)
                                    }
                                },
                        )
                    }
                }
                if (enableSwipeToStart) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxWidth(swipeToStartThreshold)
                            .padding(start = 16.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = endIcon),
                            tint = Color.Red,
                            contentDescription = "endIcon",
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .clickable {
                                    scope.launch {
                                        offsetX = 0f
                                        onSwipeEndToStart(item)
                                    }
                                },

                            )
                    }
                }

                Box(
                    modifier = Modifier
                        .offset { IntOffset(offsetX.roundToInt(), 0) }
                        .fillMaxWidth()
                        .draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { delta ->
                                val newOffsetX =
                                    (offsetX + delta).coerceIn(minOffsetX, maxOffsetX)
                                if (newOffsetX != offsetX) {
                                    offsetX = newOffsetX
                                }
                            },
                            onDragStopped = {
                                scope.launch {
                                    offsetX = if (offsetX > boxSize * swipeToEndThreshold / 2) {
                                        maxOffsetX
                                    } else if (offsetX < -boxSize * swipeToStartThreshold / 2) {
                                        minOffsetX
                                    } else {
                                        0f
                                    }
                                }
                            }
                        )
                ) {
                    content(item, index)
                }
            }
        }
    }
}