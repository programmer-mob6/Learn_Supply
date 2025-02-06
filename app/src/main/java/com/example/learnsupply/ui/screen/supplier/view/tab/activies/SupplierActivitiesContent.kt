package com.example.learnsupply.ui.screen.supplier.view.tab.activies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tagsamurai.tscomponents.card.AdaptiveCardItem
import com.tagsamurai.tscomponents.chip.Chip
import com.tagsamurai.tscomponents.model.Severity
import com.tagsamurai.tscomponents.model.TypeChip
import com.tagsamurai.tscomponents.textfield.UserRecord
import com.tagsamurai.tscomponents.theme.theme
import com.tagsamurai.tscomponents.utils.Spacer.heightBox
import com.tagsamurai.tscomponents.utils.Spacer.widthBox
import com.tagsamurai.tscomponents.utils.bodyStyle
import com.tagsamurai.tscomponents.utils.paddingList
import com.tagsamurai.tscomponents.utils.titleStyle

@Composable
fun SupplierActivitiesContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = paddingList,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(5) {
            SupplierActivitiesListItem()
        }
    }
}

@Composable
fun SupplierActivitiesListItem() {
    AdaptiveCardItem {
        Column {
            Row {
                Chip(
                    label = "Completed",
                    type = TypeChip.BULLET,
                    severity = Severity.SUCCESS
                )
                4.widthBox()
                Chip(
                    label = "Reception",
                    type = TypeChip.PILL,
                    severity = Severity.SUPPLY,
                    truncateText = false
                )
            }
            4.heightBox()
            Row {
                Text(
                    text = "RCP-230929-0100",
                    style = titleStyle,
                    color = theme.bodyText
                )
                Spacer(modifier = Modifier.weight(1f))
                Chip(
                    label = "Ruang Office",
                    type = TypeChip.PILL,
                    severity = Severity.SUPPLY
                )
            }
            4.heightBox()
            Row {
                Text(
                    text = "PT. ABC Indonesia",
                    style = bodyStyle,
                    color = theme.bodyText
                )
                Spacer(modifier = Modifier.weight(1f))
                UserRecord(username = "Jhon Doe")
            }
            4.heightBox()
            Row {
                Text(
                    text = "3 Item(s) 120 Stock(s)",
                    style = bodyStyle,
                    color = theme.bodyText
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Fri 29 Sept 2023 11:00:50",
                    style = bodyStyle,
                    color = theme.bodyText
                )
            }
        }
    }
}