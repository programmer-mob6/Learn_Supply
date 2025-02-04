package com.example.learnsupply.ui.screen.supplier.view.tab.list.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.ui.screen.supplier.view.tab.list.uistate.SupplierListUiState
import com.tagsamurai.tscomponents.R
import com.tagsamurai.tscomponents.bottomsheet.BottomSheet
import com.tagsamurai.tscomponents.button.ActionButton
import com.tagsamurai.tscomponents.switch.CustomSwitch
import com.tagsamurai.tscomponents.theme.theme
import com.tagsamurai.tscomponents.utils.titleStyle

@Composable
fun SupplierActionSheet(
    onDismissRequest: (Boolean) -> Unit,
    uiState: SupplierListUiState,
    item: SupplierEntity,
    showSheet: Boolean,
    onUpdateActive: ((Boolean) -> Unit)? = null,
    onDetail: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {

    BottomSheet(onDismissRequest = onDismissRequest, isShowSheet = showSheet) {
        Column {
            if (uiState.itemSelected.isEmpty()) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = 24.dp
                    )
                ) {
                    Text(
                        text = item.companyName,
                        style = titleStyle,
                        color = theme.bodyText
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    CustomSwitch(
                        value = item.isActive == "Active",
                        onValueChange = { state ->
                            onUpdateActive?.invoke(item.isActive != "Active")
                        }
                    )
                }
                ActionButton(
                    onClickAction = { onEdit?.invoke() },
                    icon = R.drawable.ic_edit_2_line_24dp,
                    title = stringResource(com.example.learnsupply.R.string.title_edit)
                )
                ActionButton(
                    onClickAction = { onDetail?.invoke() },
                    icon = R.drawable.ic_file_info_line_24dp,
                    title = stringResource(com.example.learnsupply.R.string.title_detail)
                )
                ActionButton(
                    onClickAction = { onDelete?.invoke() },
                    icon = R.drawable.ic_delete_bin_6_line_24dp,
                    iconTint = theme.danger,
                    title = stringResource(com.example.learnsupply.R.string.title_delete),
                    textColor = theme.danger
                )
            } else {
                ActionButton(
                    onClickAction = { onUpdateActive?.invoke(true) },
                    icon = R.drawable.ic_check_line_24dp,
                    title = stringResource(com.example.learnsupply.R.string.title_active)
                )
                ActionButton(
                    onClickAction = { onUpdateActive?.invoke(false) },
                    icon = R.drawable.ic_close_line_24dp,
                    title = stringResource(com.example.learnsupply.R.string.title_inactive)
                )
                ActionButton(
                    onClickAction = { onDelete?.invoke() },
                    icon = R.drawable.ic_delete_bin_6_line_24dp,
                    iconTint = theme.danger,
                    title = stringResource(com.example.learnsupply.R.string.title_delete),
                    textColor = theme.danger
                )
            }
        }
    }

}