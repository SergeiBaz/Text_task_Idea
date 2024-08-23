package com.example.test_task.feature.home.internal.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test_task.ui.theme.BlackPrimary
import com.example.test_task.ui.theme.BlackSecondary
import com.example.test_task.ui.theme.Medium14
import com.example.test_task.ui.theme.White
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ListChipsCard(
    chips: PersistentList<String>,
    modifier: Modifier = Modifier,
    onItemClick: ((Int) -> Unit)? = null
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
    ) {
        chips.forEachIndexed { index, chip ->
            ChipCard(
                chip = chip,
                onClick = {
                    onItemClick?.let { it(index) }
                },
                modifier = Modifier.wrapContentWidth()
            )
        }
    }
}

@Composable
internal fun ChipCard(
    chip: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        border = BorderStroke(0.5.dp, BlackSecondary),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .wrapContentHeight()
            .clickable { onClick?.invoke() }
    ) {
        Text(
            text = chip,
            style = Medium14,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
                .wrapContentHeight()
        )
    }
}

@Composable
@Preview
private fun ChipsCardPreview() {
    Surface(
        modifier = Modifier
            .background(BlackPrimary)
    ) {
        ListChipsCard(
            chips = persistentListOf(
                "iPhone 131111111",
                "iPhone 13",
                "iPhone 13",
                "iPhone 13",
                "iPhone 13",
                "iPhone 13",
                "iPhone 13",
                "iPhone 13"
            ),
            onItemClick = {}
        )
    }
}