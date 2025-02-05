package com.example.learnsupply.ui.screen.detailsupplier.view.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.learnsupply.ui.screen.detailsupplier.uistate.SupplierDetailUiState
import com.tagsamurai.tscomponents.R
import com.tagsamurai.tscomponents.bottomsheet.BottomSheet
import com.tagsamurai.tscomponents.button.ActionButton
import com.tagsamurai.tscomponents.theme.theme

@Composable
fun SupplierDetailActionSheet(
    onDismissRequest: (Boolean) -> Unit,
    showSheet: Boolean,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    BottomSheet(onDismissRequest = onDismissRequest, isShowSheet = showSheet) {
        Column {
            ActionButton(
                onClickAction = { onEdit?.invoke() },
                icon = R.drawable.ic_edit_2_line_24dp,
                title = stringResource(com.example.learnsupply.R.string.title_edit)
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