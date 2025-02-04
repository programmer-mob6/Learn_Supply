package com.example.learnsupply.ui.screen.detailsupplier.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.R
import com.example.learnsupply.ui.screen.detailsupplier.uistate.SupplierDetailUiState
import com.tagsamurai.tscomponents.chip.Chip
import com.tagsamurai.tscomponents.model.TypeChip
import com.tagsamurai.tscomponents.shimmerEffect.ShimmerEffect
import com.tagsamurai.tscomponents.textfield.UserRecord
import com.tagsamurai.tscomponents.theme.LocalTheme
import com.tagsamurai.tscomponents.theme.SP12
import com.tagsamurai.tscomponents.theme.W400
import com.tagsamurai.tscomponents.theme.W500
import com.tagsamurai.tscomponents.theme.W600
import com.tagsamurai.tscomponents.utils.Spacer.heightBox
import com.tagsamurai.tscomponents.utils.Spacer.widthBox
import com.tagsamurai.tscomponents.utils.Utils.toDateFormatter

@Composable
fun SupplierDetailHeader(
    uiState: SupplierDetailUiState,
    onClickAction: () -> Unit
) {
    val theme = LocalTheme.current
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(theme.accordion)
                .padding(vertical = 8.dp, horizontal = 16.dp),
        ) {
            if (uiState.isLoading) {
                LoadingContent()
            } else {
                HeaderContent(
                    uiState.supplierDetail,
                    onClickAction = onClickAction
                )
            }
        }
        HorizontalDivider(color = theme.lineStroke)
    }
}

@Composable
private fun HeaderContent(
    supplierDetail: SupplierEntity,
    onClickAction: () -> Unit
) {
    val theme = LocalTheme.current
    Column {
        Chip(
            label = supplierDetail.isActive,//readerDetail.networkStatus.ifNullOrBlank { "-" },
            type = TypeChip.BULLET,
//            severity = ,//readerDetail.networkStatusSeverity,
            truncateText = false,
        )
        8.heightBox()
        Text(
            text = supplierDetail.companyName,//readerDetail.name.ifNullOrBlank { "-" },
            style = SP12.W600,
            color = theme.bodyText,
            lineHeight = 16.sp,
        )
//        Row {
        Column {
            Row {
                Text(
                    text = "Modified by: ",//readerDetail.name.ifNullOrBlank { "-" },
                    style = SP12.W400,
                    color = theme.bodyText,
                    lineHeight = 16.sp,
                )
                UserRecord(username = supplierDetail.pic) //data.manager.ifNullOrBlank { "" }
            }

            4.heightBox()
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
//                    readerDetail.groupName?.let {
                Text(
                    text = supplierDetail.lastModified.toDateFormatter(),
                    style = SP12.W400
                )
//                    }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.title_detail),
                    style = SP12.W500,
                    color = theme.buttonPrimary,
                    modifier = Modifier.clickable { onClickAction() }
                )
            }
        }
//        }
    }
}

@Composable
private fun LoadingContent() {
    Column {
        ShimmerEffect(width = 50.dp)
        12.heightBox()
        Row {
            ShimmerEffect(
                width = 40.dp,
                height = 40.dp,
                borderRadius = 7.dp,
            )
            12.widthBox()
            Column {
                ShimmerEffect(width = 150.dp)
                6.heightBox()
                ShimmerEffect(width = 50.dp)
            }
        }
    }
}
