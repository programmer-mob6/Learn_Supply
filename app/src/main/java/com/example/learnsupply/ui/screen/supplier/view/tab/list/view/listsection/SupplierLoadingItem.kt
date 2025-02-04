package com.example.learnsupply.ui.screen.supplier.view.tab.list.view.listsection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tagsamurai.tscomponents.card.AdaptiveCardItem
import com.tagsamurai.tscomponents.shimmerEffect.ShimmerEffect
import com.tagsamurai.tscomponents.utils.Spacer.heightBox
import com.tagsamurai.tscomponents.utils.Spacer.widthBox
import com.tagsamurai.tscomponents.utils.itemGap4

@Composable
fun SupplierLoadingItem() {
    AdaptiveCardItem(showMoreIcon = true) {
        Column {
            ShimmerEffect(width = 120.dp)
            itemGap4.heightBox()
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row {
                    ShimmerEffect(width = 60.dp)
                    4.widthBox()
                    ShimmerEffect(width = 60.dp)
                }
                Spacer(Modifier.weight(1f))
                ShimmerEffect(width = 80.dp)
            }
        }
    }
}