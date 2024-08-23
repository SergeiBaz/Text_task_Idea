package com.example.test_task.feature.home.internal.components.dialog.redaction_dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.test_task.R
import com.example.test_task.feature.home.until.DialogState
import com.example.test_task.ui.theme.BlackThree
import com.example.test_task.ui.theme.BluePrimary
import com.example.test_task.ui.theme.Medium20
import com.example.test_task.ui.theme.RedPrimary
import com.example.test_task.ui.theme.SemiBold18
import com.example.test_task.ui.theme.White

@Composable
internal fun RedactionDialog(
    modifier: Modifier = Modifier,
    state: DialogState<RedactionDialogData>,
    onDismiss: () -> Unit = {},
    onCancel: () -> Unit = {},
    onAccept: (id: Int?, amount: Int) -> Unit = { _, _ -> },
) {
    if (state is DialogState.Visible) {
        with(state.data) {
            var localQuality by remember { mutableIntStateOf(quantity) }

            Dialog(
                properties = DialogProperties(usePlatformDefaultWidth = false),
                onDismissRequest = onDismiss,
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(
                            top = 24.dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    ) {
                        Text(
                            text = primaryText,
                            style = Medium20,
                            textAlign = TextAlign.Center
                        )

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp),
                        ) {
                            IconButton(
                                onClick = { if (localQuality > 0) localQuality-- }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_minus),
                                    contentDescription = "minus"
                                )
                            }
                            Spacer(modifier = Modifier.width(24.dp))
                            Text(text = localQuality.toString(), style = SemiBold18)
                            Spacer(modifier = Modifier.width(24.dp))
                            IconButton(
                                onClick = { localQuality++ }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_plus),
                                    contentDescription = "plus"
                                )
                            }
                        }


                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            secondaryTextButton?.let {
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = White
                                    ),
                                    border = BorderStroke(1.dp, BlackThree),
                                    shape = RoundedCornerShape(16.dp),
                                    onClick = onCancel,
                                    modifier = Modifier
                                        .weight(1f),
                                ) {
                                    Text(
                                        text = stringResource(id = it),
                                        color = RedPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                            }

                            primaryTextButton?.let {
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = White
                                    ),
                                    border = BorderStroke(1.dp, BlackThree),
                                    shape = RoundedCornerShape(16.dp),
                                    onClick = { onAccept(id, localQuality) },
                                    modifier = Modifier
                                        .weight(1f),
                                ) {
                                    Text(
                                        text = stringResource(id = it),
                                        color = BluePrimary
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun RedactionDialogPreview() {
    Surface {
        RedactionDialog(
            state = DialogState.Visible(
                RedactionDialogData(
                    primaryText = stringResource(id = R.string.quantity_product),
                    quantity = 0,
                )
            ),
        )
    }
}