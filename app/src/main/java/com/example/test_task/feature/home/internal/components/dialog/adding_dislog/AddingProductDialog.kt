package com.example.test_task.feature.home.internal.components.dialog.adding_dislog

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.test_task.R
import com.example.test_task.feature.home.internal.components.container.InputsContainerForProduct
import com.example.test_task.feature.home.until.DialogState
import com.example.test_task.ui.theme.Background
import com.example.test_task.ui.theme.BlackThree
import com.example.test_task.ui.theme.BluePrimary
import com.example.test_task.ui.theme.Medium20
import com.example.test_task.ui.theme.RedPrimary
import com.example.test_task.ui.theme.White

@Composable
internal fun AddingProductDialog(
    modifier: Modifier = Modifier,
    state: DialogState<AddingProductDialogData>,
    name: String = "",
    tags: String = "",
    amount: String = "",
    onValueChangeName: (String) -> Unit = {},
    onValueChangeAmount: (String) -> Unit = {},
    onValueChangeTags: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
    onCancel: () -> Unit = {},
    onAccept: () -> Unit = {},
) {
    if (state is DialogState.Visible) {
        with(state.data) {
            Dialog(
                properties = DialogProperties(usePlatformDefaultWidth = false),
                onDismissRequest = onDismiss,
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Background, shape = RoundedCornerShape(8.dp)),
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
                            text = title,
                            style = Medium20,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        InputsContainerForProduct(
                            name = name,
                            amount = amount,
                            tags = tags,
                            onValueChangeName = onValueChangeName,
                            onValueChangeAmount = onValueChangeAmount,
                            onValueChangeTags = onValueChangeTags,
                        )

                        Spacer(modifier = Modifier.height(32.dp))

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
                                    onClick = onAccept,
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
private fun AddingProductPreview() {
    Surface {
        AddingProductDialog(
            state = DialogState.Visible(
                AddingProductDialogData(
                    title = stringResource(id = R.string.adding_product),
                )
            ),
        )
    }
}
