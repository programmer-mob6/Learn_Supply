package com.example.learnsupply.ui.screen.supplier.view.tab.activies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tagsamurai.tscomponents.card.AdaptiveCardItem
import com.tagsamurai.tscomponents.shimmerEffect.ShimmerEffect
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar
import com.tagsamurai.tscomponents.utils.Spacer.heightBox
import com.tagsamurai.tscomponents.utils.paddingList

@Composable
fun SupplierActivitiesContent(
    navigateTo: (String) -> Unit,
    onShowSnackBar: OnShowSnackBar,
) {
    Column(modifier = Modifier.padding(paddingList)) {
        repeat(5) {
            SupplierActivitiesListItemLoading()
            4.heightBox()
        }
    }
}

@Composable
fun SupplierActivitiesListItemLoading() {
    AdaptiveCardItem {
        Column {
            Row {
                ShimmerEffect(width = 130.dp)
                Spacer(modifier = Modifier.weight(1f))
                ShimmerEffect(width = 50.dp)
            }
            4.heightBox()
            Row {
                ShimmerEffect(width = 100.dp)
                Spacer(modifier = Modifier.weight(1f))
                ShimmerEffect(width = 60.dp)
            }
        }
    }
}