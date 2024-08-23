package com.example.test_task.feature.home.internal.components.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test_task.R
import com.example.test_task.ui.theme.Background
import com.example.test_task.ui.theme.BlackThree
import com.example.test_task.ui.theme.Regular14
import com.example.test_task.ui.theme.White

@Composable
internal fun InputsContainerForProduct(
    modifier: Modifier = Modifier,
    name: String = "",
    amount: String = "",
    tags: String = "",
    onValueChangeName: (String) -> Unit = {},
    onValueChangeAmount: (String) -> Unit = {},
    onValueChangeTags: (String) -> Unit = {},
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState()),
    ) {
        InputTextField(
            value = name,
            label = stringResource(R.string.name),
            onValueChange = onValueChangeName
        )

        InputTextField(
            value = amount,
            label = stringResource(R.string.quantity),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onValueChangeAmount
        )

        InputTextField(
            value = tags,
            label = stringResource(R.string.tags_separated),
            onValueChange = onValueChangeTags
        )
    }
}

@Preview
@Composable
private fun InputsContainerForProductPreview() {
    Surface(
        color = Background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        InputsContainerForProduct()
    }
}

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = Regular14,
                color = BlackThree
            )
        },
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            errorContainerColor = White,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = White,
            unfocusedContainerColor = White,
        ),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
    )
}

