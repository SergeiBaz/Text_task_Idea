package com.example.test_task.feature.home.internal.components.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_task.R
import com.example.test_task.ui.theme.BlackThree
import com.example.test_task.ui.theme.BluePrimary
import com.example.test_task.ui.theme.Medium16

@Composable
internal fun SearchTextField(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    onDone: KeyboardActionScope.() -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions(onDone),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextFieldValueChange: (String) -> Unit,
    onCrossIconButtonClick: () -> Unit = {},
) {
    BasicTextField(
        value = text,
        onValueChange = onTextFieldValueChange,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    contentDescription = "Searh",
                    modifier = Modifier
                        .padding(16.dp)
                )

                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = Medium16,
                            color = BlackThree,
                        )
                    }
                    innerTextField()
                }

                if (text.isNotBlank()) {
                    IconButton(
                        onClick = onCrossIconButtonClick
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_cross),
                            contentDescription = "cross"
                        )
                    }
                }
            }
        },
        visualTransformation = visualTransformation,
        singleLine = true,
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(
            autoCorrect = true,
            keyboardType = keyboardType,
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 19.sp
        ),
        cursorBrush = SolidColor(BluePrimary),
        modifier = modifier,
    )
}


@Preview
@Composable
private fun SearchTextFieldPreview() {
    SearchTextField(
        text = "123",
        onTextFieldValueChange = {}
    )
}

@Preview
@Composable
private fun SearchTextFieldEmptyPreview() {
    SearchTextField(
        text = "",
        placeholder = "Поиск товаров",
        onTextFieldValueChange = {}
    )
}