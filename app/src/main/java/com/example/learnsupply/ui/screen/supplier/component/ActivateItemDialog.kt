package com.example.learnsupply.ui.screen.supplier.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.R
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
    val status = if (isActive) "Activate" else "Inactivate"

    val messageResId = when {
        supplier.size > 1 && isActive -> R.string.message_bulk_activate_supplier
        supplier.size > 1 && !isActive -> R.string.message_bulk_inactivate_supplier
        isActive -> R.string.message_single_activate_supplier
        else -> R.string.message_single_inactivate_supplier
    }

    val rawContent = stringResource(
        messageResId,
        if (supplier.size > 1) "${supplier.size} supplier(s)" else supplier.firstOrNull()?.companyName
            ?: ""
    )

    val message = rawContent.generateAnnotated()


    val buttonConfirmText = if (isActive) {
        stringResource(R.string.title_activate)
    } else {
        stringResource(R.string.title_inactivate)
    }

    val buttonSeverity = if (isActive) {
        Severity.SUPPLY
    } else {
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
            title = stringResource(
                R.string.title_set_activation_supplier,
                status
            ),
            textButtonCancel = stringResource(R.string.title_cancel),
            textButtonConfirm = buttonConfirmText,
            severity = buttonSeverity,
            content = message
        )
    }
}