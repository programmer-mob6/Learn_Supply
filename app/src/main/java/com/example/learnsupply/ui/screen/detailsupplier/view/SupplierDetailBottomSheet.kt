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
//    onClickAction: () -> Unit,
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
                    label = supplierDetail.isActive,//"Active",//supplierDetail.isActive,
                    type = TypeChip.BULLET,
//                    severity = supplierDetail.statusSeverity,
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
//                            onClickAction()
                            showSuppliedItem = true
                        }
                    )
                }
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.pic)
                ) {
                    UserRecord(username = supplierDetail.pic) //supplierDetail.pic
                }
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.pic_phone),
                    content = supplierDetail.picPhone,//"(+62)21 6672812",//supplierDetail.name.ifNullOrBlank { "-" },
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.pic_email),
                    content = supplierDetail.picEmail,//"Budiu@mailnesia.com",//supplierDetail.name.ifNullOrBlank { "-" },
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.country),
                    content = supplierDetail.country,//"Indonesia",//supplierDetail.name.ifNullOrBlank { "-" },
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.state),
                    content = supplierDetail.state,//"Indonesia",//supplierDetail.name.ifNullOrBlank { "-" },
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.city),
                    content = supplierDetail.city,//"DKI Jakarta",//supplierDetail.name.ifNullOrBlank { "-" },
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.zip_code),
                    content = supplierDetail.zipCode,//"13440",//supplierDetail.name.ifNullOrBlank { "-" },
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.company_phone),
                    content = supplierDetail.companyPhone,//"(+62)21 6672812",//supplierDetail.name.ifNullOrBlank { "-" },
                    style = bodyStyle,
                    color = theme.bodyText
                )
                itemGap8.heightBox()
                DetailRecordBottomSheet(
                    label = stringResource(id = R.string.company_address),
                    content = supplierDetail.companyAddress,//"Jalan Villa Gading Indah Blok A1 No.1 Ruko Boulevard",//supplierDetail.name.ifNullOrBlank { "-" },
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