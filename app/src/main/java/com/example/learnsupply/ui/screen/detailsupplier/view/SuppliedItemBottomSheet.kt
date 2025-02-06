package com.example.learnsupply.ui.screen.detailsupplier.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.R
import com.tagsamurai.tscomponents.bottomsheet.BottomSheet
import com.tagsamurai.tscomponents.chip.Chip
import com.tagsamurai.tscomponents.model.Severity
import com.tagsamurai.tscomponents.model.TypeChip
import com.tagsamurai.tscomponents.rowdata.DetailRecordBottomSheet
import com.tagsamurai.tscomponents.theme.theme
import com.tagsamurai.tscomponents.utils.Spacer.heightBox
import com.tagsamurai.tscomponents.utils.Spacer.widthBox
import com.tagsamurai.tscomponents.utils.bodyStyle
import com.tagsamurai.tscomponents.utils.itemGap4
import com.tagsamurai.tscomponents.utils.itemGap8
import com.tagsamurai.tscomponents.utils.titleStyle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SuppliedItemBottomSheet(
    supplierDetail: SupplierEntity,
    showSheet: Boolean,
    onDismissRequest: (Boolean) -> Unit,
) {

    BottomSheet(
        isShowSheet = showSheet,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                Modifier
                    .padding(horizontal = 30.dp)
            ) {
                Row {
                    Icon(
                        tint = theme.buttonPrimary,
                        painter = painterResource(com.tagsamurai.tscomponents.R.drawable.ic_information_line_24dp),
                        contentDescription = "Supplied Item",
                        modifier = Modifier.size(18.dp)
                    )
                    8.widthBox()
                    Text(
                        text = "Supplied Item",
                        style = titleStyle,
                        color = theme.bodyText
                    )
                }
                itemGap8.heightBox()
                supplierDetail.suppliedItem.forEach {
                    DetailRecordBottomSheet(
                        label = stringResource(id = R.string.item),
                        content = it.itemName,
                        style = bodyStyle,
                        color = theme.bodyText
                    )
                    itemGap4.heightBox()
                    DetailRecordBottomSheet(
                        label = stringResource(id = R.string.sku)
                    ) {
                        FlowRow(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            it.itemSku.forEach { itemSku ->
                                Chip(
                                    label = itemSku,
                                    type = TypeChip.PILL,
                                    severity = Severity.SECONDARY,
                                    truncateText = false
                                )
                            }
                        }
                    }
                }
            }
        }
        24.heightBox()
    }
}