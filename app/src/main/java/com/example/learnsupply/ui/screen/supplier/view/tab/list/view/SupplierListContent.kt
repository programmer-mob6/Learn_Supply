package com.example.learnsupply.ui.screen.supplier.view.tab.list.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.R
import com.example.learnsupply.ui.screen.addsupplier.view.AddSupplierDialog
import com.example.learnsupply.ui.screen.supplier.view.tab.list.view.listsection.SupplierListSection
import com.example.learnsupply.ui.screen.supplier.view.tab.list.viewmodel.SupplierListViewModel
import com.tagsamurai.tscomponents.handlestate.HandleState
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar

@Composable
fun SupplierListContent(
    navigateTo: (String) -> Unit,
    onShowSnackBar: OnShowSnackBar,
) {
    val supplierlistViewModel: SupplierListViewModel = hiltViewModel()
    val uiState = supplierlistViewModel.uiState.collectAsStateWithLifecycle()
    val supplierCallback = supplierlistViewModel.getCallback()

    var showCreateDialog by remember { mutableStateOf(false) }
    var data: SupplierEntity? by remember { mutableStateOf(null) }

    HandleState(
        state = uiState.value.deleteState,
        onShowSnackBar = onShowSnackBar,
        successMsg = stringResource(R.string.message_success_delete_supplier),
        errorMsg = stringResource(R.string.message_error_delete_supplier),
        onDispose = supplierCallback.onResetMessageState
    )

    val status = if (uiState.value.activation) "Activate" else "Inactivate"
    HandleState(
        state = uiState.value.activateState,
        onShowSnackBar = onShowSnackBar,
        successMsg = stringResource(R.string.message_success_activate_supplier, status),
        errorMsg = stringResource(R.string.message_error_activate_supplier),
        onDispose = supplierCallback.onResetMessageState
    )

//    HandleState(
//        state = uiState.value.editState,
//        onShowSnackBar = onShowSnackBar,
//        successMsg = stringResource(R.string.message_success_edit_supplier),
//        errorMsg = stringResource(R.string.message_error_edit_supplier),
//        onDispose = supplierCallback.onRefresh
//    )

    LaunchedEffect(Unit) {
        supplierlistViewModel.init()
    }

    SupplierListSection(
        onNavigateTo = navigateTo,
        uiState = uiState.value,
        supplierCallback = supplierCallback,
        onEditAsset = { value ->
            data = value
            showCreateDialog = true
        }
    )

    AddSupplierDialog(
        onDismissRequest = { state ->
            showCreateDialog = state
            data = null
        },
        showDialog = showCreateDialog,
        id = data?.id,
        onShowSnackBar = onShowSnackBar,
        onSuccess = supplierCallback.onRefresh
    )
}