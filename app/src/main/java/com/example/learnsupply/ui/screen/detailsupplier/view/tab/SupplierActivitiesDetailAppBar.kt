package com.example.learnsupply.ui.screen.detailsupplier.view.tab

import androidx.compose.runtime.Composable
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
import com.example.learnsupply.ui.screen.detailsupplier.view.SupplierFilterDetailSheet
import com.example.learnsupply.ui.screen.detailsupplier.viewmodel.SupplierDetailViewModel
import com.example.learnsupply.ui.screen.supplier.component.DeleteItemDialog
import com.tagsamurai.tscomponents.handlestate.HandleState
import com.tagsamurai.tscomponents.loading.LoadingOverlay
import com.tagsamurai.tscomponents.model.Menu
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar
import com.tagsamurai.tscomponents.textfield.SearchFieldTopAppBar
import com.tagsamurai.tscomponents.topappbar.TopAppBar

@Composable
fun SupplierActivitiesDetailAppBar(
    itemId: String,
    navigateUp: () -> Unit,
    onShowSnackbar: OnShowSnackBar,
    viewmodel: SupplierDetailViewModel = hiltViewModel()
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val cb = viewmodel.getCallback()

    var data: SupplierEntity? by remember { mutableStateOf(null) }

    var showSearch by remember { mutableStateOf(false) }
    var showCreateDialog by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var showActionSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val listMenu = getListMenu()

    HandleState(
        state = uiState.deleteState,
        onShowSnackBar = onShowSnackbar,
        successMsg = stringResource(R.string.message_success_delete_supplier),
        errorMsg = stringResource(R.string.message_error_delete_supplier),
        onSuccess = {
            navigateUp()
        },
        onDispose = cb.onResetMessageState
    )

    if (showSearch) {
        SearchFieldTopAppBar(
            onNavigateUp = { showSearch = false },
            onSearchConfirm = cb.onSearch
        )
    } else {
        TopAppBar(
            menu = listMenu,
            canNavigateBack = true,
            onMenuAction = { menu ->
                when (menu) {
                    Menu.SEARCH -> showSearch = true
                    Menu.FILTER -> showFilterSheet = true
                    Menu.OTHER -> showActionSheet = true
                    else -> Unit
                }
            },
            navigateUp = navigateUp
        )
    }
    SupplierFilterDetailSheet(
        onDismissRequest = { state -> showFilterSheet = state },
        uiState = uiState,
        showFilter = showFilterSheet,
        onApplyConfirm = cb.onFilter
    )

    SupplierDetailActionSheet(
        onDismissRequest = { showActionSheet = it },
        showSheet = showActionSheet,
        onEdit = { showCreateDialog = true },
        onDelete = { showDeleteDialog = true },
    )

    AddSupplierDialog(
        onDismissRequest = { state ->
            showCreateDialog = state
            data = null
        },
        showDialog = showCreateDialog,
        id = itemId,
        onShowSnackBar = onShowSnackbar,
        onSuccess = {
            viewmodel.getDetailSupplier()
            showActionSheet = false
        }
    )

    DeleteItemDialog(
        onDismissRequest = { showDeleteDialog = it },
        supplier = listOf(uiState.supplierDetail),
        showDialog = showDeleteDialog,
        onConfirm = {
            cb.onDeleteSupplierById()
            showActionSheet = false
        }
    )
    LoadingOverlay(uiState.isLoadingOverlay)
}

private fun getListMenu(): List<Menu> {
    val menu = listOf(Menu.SEARCH, Menu.FILTER, Menu.OTHER)

    return menu
}