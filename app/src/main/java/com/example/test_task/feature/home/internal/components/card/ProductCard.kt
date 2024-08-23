package com.example.test_task.feature.home.internal.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test_task.R
import com.example.test_task.feature.home.internal.model.ProductUi
import com.example.test_task.ui.theme.BlackFour
import com.example.test_task.ui.theme.BlackPrimary
import com.example.test_task.ui.theme.BlackThree
import com.example.test_task.ui.theme.Medium12
import com.example.test_task.ui.theme.Medium16
import com.example.test_task.ui.theme.Regular14
import com.example.test_task.ui.theme.SemiBold18
import com.example.test_task.ui.theme.White
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ProductCard(
    productUi: ProductUi,
    modifier: Modifier = Modifier,
    onItemClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .clickable(enabled = onItemClick != null) {
                onItemClick?.invoke()
            }
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.elevatedCardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = productUi.name.orEmpty(),
                    style = SemiBold18,
                    color = BlackPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(0.7f)
                )
                productUi.time?.let {
                    Text(
                        text = it,
                        style = Regular14,
                        color = BlackThree,
                    )
                }

            }
            HorizontalDivider(
                color = BlackFour,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .offset(y = 8.dp),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {
                ListChipsCard(
                    chips = productUi.tags,
                    onItemClick = {},
                    modifier = Modifier
                        .weight(0.8f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(0.3f)
                ) {
                    Text(
                        text = stringResource(R.string.in_warehouse),
                        style = Regular14,
                        color = BlackThree,
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = productUi.amount ?: stringResource(R.string.not_available),
                        style = if (productUi.amount != null) Medium16
                        else Medium12,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }
}

@Composable
@Preview
private fun ProductCardPreview() {
    Surface(
        modifier = Modifier
            .background(BlackPrimary)
    ) {
        ProductCard(
            productUi = ProductUi(
                name = "iPhone",
                time = "02.10.2021",
                amount = "15",
                tags = persistentListOf("Игровая приставка", "iPhone 13", "iPhone 13")
            ),
            onItemClick = {}
        )
    }
}