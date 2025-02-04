package com.example.learnsupply.ui.screen.supplier.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.tagsamurai.tscomponents.alertdialog.AlertDialog
import com.tagsamurai.tscomponents.model.Severity
import com.tagsamurai.tscomponents.theme.theme
import com.tagsamurai.tscomponents.utils.Utils.generateAnnotated

@Composable
fun DeleteItemDialog(
    onDismissRequest: (Boolean) -> Unit,
    supplier: List<SupplierEntity>,
    showDialog: Boolean,
    onConfirm: (List<SupplierEntity>) -> Unit
) {
    val rawContent = if (supplier.size > 1) {
        stringResource(com.example.learnsupply.R.string.message_bulk_delete_supplier, "${supplier.size} Data")
    } else {
        stringResource(com.example.learnsupply.R.string.message_single_delete_supplier, supplier.firstOrNull()?.companyName ?: "")
    }
    val message = rawContent.generateAnnotated()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            onButtonCancel = { onDismissRequest(false) },
            onButtonConfirm = {
                onDismissRequest(false)
                onConfirm(supplier)
            },
            icon = com.tagsamurai.tscomponents.R.drawable.ic_delete_bin_6_line_24dp,
            iconColor = theme.danger,
            title = stringResource(com.example.learnsupply.R.string.title_delete_supplier),
            textButtonCancel = stringResource(com.example.learnsupply.R.string.title_cancel),
            textButtonConfirm = stringResource(com.example.learnsupply.R.string.title_delete),
            severity = Severity.DANGER,
            content = message
        )
    }
}