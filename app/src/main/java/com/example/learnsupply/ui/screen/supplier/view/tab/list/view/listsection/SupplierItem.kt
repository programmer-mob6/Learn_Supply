package com.example.learnsupply.ui.screen.supplier.view.tab.list.view.listsection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.R
import com.example.learnsupply.navigation.NavigationRoute
import com.example.learnsupply.ui.screen.supplier.component.ActivateItemDialog
import com.example.learnsupply.ui.screen.supplier.component.DeleteItemDialog
import com.example.learnsupply.ui.screen.supplier.view.tab.list.model.SupplierListCallback
import com.example.learnsupply.ui.screen.supplier.view.tab.list.uistate.SupplierListUiState
import com.example.learnsupply.ui.screen.supplier.view.tab.list.view.SupplierActionSheet
import com.tagsamurai.tscomponents.card.AdaptiveCardItem
import com.tagsamurai.tscomponents.chip.Chip
import com.tagsamurai.tscomponents.model.Severity
import com.tagsamurai.tscomponents.model.TypeChip
import com.tagsamurai.tscomponents.textfield.UserRecord
import com.tagsamurai.tscomponents.theme.SP12
import com.tagsamurai.tscomponents.theme.W400
import com.tagsamurai.tscomponents.theme.theme
import com.tagsamurai.tscomponents.utils.Spacer.heightBox
import com.tagsamurai.tscomponents.utils.Spacer.widthBox
import com.tagsamurai.tscomponents.utils.Utils.toDateFormatter
import com.tagsamurai.tscomponents.utils.bodyStyle
import com.tagsamurai.tscomponents.utils.itemGap4
import com.tagsamurai.tscomponents.utils.titleStyle

@Composable
fun SupplierItem(
    uiState: SupplierListUiState,
    item: SupplierEntity,
    cb: SupplierListCallback,
    onNavigateTo: (String) -> Unit,
    onEditAsset: (SupplierEntity) -> Unit
) {
    var showActionSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showActiveDialog by remember { mutableStateOf(false) }
    var isActivate: Boolean? by remember { mutableStateOf(null) }
    val isSelected = uiState.itemSelected.contains(item)
    AdaptiveCardItem(
        onClick = {
            if (uiState.itemSelected.isNotEmpty()) cb.onUpdateItemSelected(
                item
            )
        },
        onLongClick = { cb.onUpdateItemSelected(item) },
        containerColor = if (isSelected) theme.popupBackgroundSelected else Color.Transparent,
        showMoreIcon = true,
        onClickAction = {
            showActionSheet = true
//            cb.onShowActionSheet(true)
        }
    ) {
        Column {
            Chip(
                label = item.isActive,
                type = TypeChip.BULLET,
                severity = if (item.isActive == "Active") Severity.SUCCESS else Severity.DANGER
            )
            itemGap4.heightBox()
            Text(
                text = item.companyName,//item.name,
                style = titleStyle,
                color = theme.bodyText
            )
            itemGap4.heightBox()
            Text(
                text = "${item.city}, " + item.country,//item.name,
                style = titleStyle,
                color = theme.bodyText
            )
            itemGap4.heightBox()
            Row(verticalAlignment = Alignment.CenterVertically) {
                item.suppliedItemSku.take(2).forEach {
                    Chip(
                        label = it,
                        type = TypeChip.PILL,
                        severity = Severity.SECONDARY
                    )
                    2.widthBox()
                }
                if (item.suppliedItemSku.size > 2) {
                    Text(
                        text = stringResource(
                            R.string.text_placeholder_more_s,
                            item.suppliedItemSku.size - 2
                        ),
                        style = bodyStyle,
                        color = theme.bodyText
                    )
                }
            }
            itemGap4.heightBox()
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row {
                    Text(
                        text = item.lastModified.toDateFormatter(),
                        style = SP12.W400
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    UserRecord(username = item.pic) //data.manager.ifNullOrBlank { "" }
                }

            }
        }

    }

    SupplierActionSheet(
        onDismissRequest = { showActionSheet = it },
        uiState = uiState,
        item = item,
        showSheet = showActionSheet,
        onDetail = {
            onNavigateTo(
                NavigationRoute.DetailScreen.navigate(
                    itemId = item.id
                )
            )
        },
        onUpdateActive = {
            isActivate = it
            showActiveDialog = true
        },
        onEdit = {
            showActionSheet = false
            onEditAsset(item)
        },
        onDelete = { showDeleteDialog = true }
    )

    DeleteItemDialog(
        onDismissRequest = { showDeleteDialog = it },
        supplier = listOf(item),
        showDialog = showDeleteDialog,
        onConfirm = { value ->
            cb.onDeleteAssetById(value.map { it.id })
            showActionSheet = false
        }
    )

    isActivate?.let { status ->
        ActivateItemDialog(
            onDismissRequest = {
                showActiveDialog = it
                isActivate = null
            },
            supplier = listOf(item),
            isActive = status,
            showDialog = showActiveDialog,
            onConfirm = { value ->
                cb.onUpdateActiveById(value.map { it.id }, status)
                showActionSheet = false
            }
        )
    }


}