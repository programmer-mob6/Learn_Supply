package com.example.learnsupply.ui.screen.detailsupplier.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.R
import com.tagsamurai.tscomponents.bottomsheet.BottomSheet
import com.tagsamurai.tscomponents.chip.Chip
import com.tagsamurai.tscomponents.model.Severity
import com.tagsamurai.tscomponents.model.TypeChip
import com.tagsamurai.tscomponents.rowdata.DetailRecordBottomSheet
import com.tagsamurai.tscomponents.textfield.UserRecord
import com.tagsamurai.tscomponents.theme.SP12
import com.tagsamurai.tscomponents.theme.W500
import com.tagsamurai.tscomponents.theme.theme
import com.tagsamurai.tscomponents.utils.Spacer.heightBox
import com.tagsamurai.tscomponents.utils.bodyStyle
import com.tagsamurai.tscomponents.utils.itemGap8

@Composable
fun SupplierDetailBottomSheet(
    supplierDetail: SupplierEntity,
    showSheet: Boolean,
    onDismissRequest: (Boolean) -> Unit,
) {
    var showSuppliedItem by remember { mutableStateOf(false) }

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
                Chip(
                    label = supplierDetail.isActive,
                    type = TypeChip.BULLET,
                    severity = if (supplierDetail.isActive == "Active") Severity.SUPPLY else Severity.DANGER,
                    truncateText = false
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.supplied_item)
                ) {
                    Text(
                        text = stringResource(id = R.string.view_supplied_item),
                        style = SP12.W500,
                        color = theme.buttonPrimary,
                        modifier = Modifier.clickable {
                            showSuppliedItem = true
                        }
                    )
                }
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.pic)
                ) {
                    UserRecord(username = supplierDetail.pic)
                }
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.pic_phone),
                    content = supplierDetail.picPhone,
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.pic_email),
                    content = supplierDetail.picEmail,
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.country),
                    content = supplierDetail.country,
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.state),
                    content = supplierDetail.state,
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.city),
                    content = supplierDetail.city,
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.zip_code),
                    content = supplierDetail.zipCode,
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.company_phone),
                    content = supplierDetail.companyPhone,
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.company_address),
                    content = supplierDetail.companyAddress,
                    style = bodyStyle,
                    color = theme.bodyText
                )
            }
        }
        24.heightBox()
    }

    SuppliedItemBottomSheet(
        supplierDetail = supplierDetail,
        showSheet = showSuppliedItem,
    ) {
        showSuppliedItem = false
    }
}