package com.example.learnsupply.ui.screen.supplier.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.tagsamurai.tscomponents.alertdialog.AlertDialog
import com.tagsamurai.tscomponents.model.Severity
import com.tagsamurai.tscomponents.theme.theme
import com.tagsamurai.tscomponents.utils.Utils.generateAnnotated

@Composable
fun ActivateItemDialog(
    onDismissRequest: (Boolean) -> Unit,
    supplier: List<SupplierEntity>,
    isActive: Boolean,
    showDialog: Boolean,
    onConfirm: (List<SupplierEntity>) -> Unit
) {
    val rawContent = if (supplier.size > 1) {
        stringResource(com.example.learnsupply.R.string.message_bulk_inactivate_supplier, "${supplier.size} supplier(s)")
    } else {
        stringResource(com.example.learnsupply.R.string.message_single_inactivate_supplier, supplier.firstOrNull()?.companyName ?: "")
    }

    val message = rawContent.generateAnnotated()

    val buttonConfirmText = if (isActive) { //suplierStatus.firstOrNull() == "Active"
        stringResource(com.example.learnsupply.R.string.title_activate)
//        stringResource(com.example.learnsupply.R.string.title_inactivate)
    } else {
//        stringResource(com.example.learnsupply.R.string.title_activate)
        stringResource(com.example.learnsupply.R.string.title_inactivate)
    }

    val buttonSeverity = if (isActive) { //suplierStatus.firstOrNull() == "Active"
        Severity.SUPPLY
//        Severity.DANGER
    } else {
//        Severity.SUPPLY
        Severity.DANGER
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            onButtonCancel = { onDismissRequest(false) },
            onButtonConfirm = {
                onDismissRequest(false)
                onConfirm(supplier)
            },
            icon = com.tagsamurai.tscomponents.R.drawable.ic_error_warning_line_24dp,
            iconColor = theme.danger,
            title = stringResource(com.example.learnsupply.R.string.title_inactivate_supplier),
            textButtonCancel = stringResource(com.example.learnsupply.R.string.title_cancel),
            textButtonConfirm = buttonConfirmText, //stringResource(com.example.learnsupply.R.string.title_inactivate),
            severity = buttonSeverity,//Severity.DANGER,
            content = message
        )
    }
}