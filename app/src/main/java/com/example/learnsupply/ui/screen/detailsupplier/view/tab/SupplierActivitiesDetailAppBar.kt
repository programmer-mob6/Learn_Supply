package com.example.learnsupply.ui.screen.detailsupplier.view.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.ui.screen.detailsupplier.uistate.SupplierDetailUiState
import com.example.learnsupply.ui.screen.detailsupplier.view.SupplierFilterDetailSheet
import com.example.learnsupply.ui.screen.detailsupplier.viewmodel.SupplierDetailViewModel
import com.example.learnsupply.ui.screen.supplier.component.DeleteItemDialog
import com.tagsamurai.tscomponents.model.Menu
import com.tagsamurai.tscomponents.textfield.SearchFieldTopAppBar
import com.tagsamurai.tscomponents.topappbar.TopAppBar

@Composable
fun SupplierActivitiesDetailAppBar(
    navigateUp: () -> Unit,
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
    val listMenu = getListMenu(uiState)


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
        uiState = uiState,
        showSheet = showActionSheet,
        onDelete = { showDeleteDialog = true },
    )

//    AddSupplierDialog(
//        onDismissRequest = { state ->
//            showCreateDialog = state
//            data = null
//        },
//        showDialog = showCreateDialog,
//        id = data?.id,
//        onShowSnackBar = onShowSnackbar,
//        onSuccess = supplierViewModel.onRefresh
//    )

    DeleteItemDialog(
        onDismissRequest = { showDeleteDialog = it },
        supplier = listOf(uiState.supplierDetail),
        showDialog = showDeleteDialog,
        onConfirm = { value ->
            cb.onDeleteSupplierById(value.map { it.id })
            showActionSheet = false
        }
    )
}

private fun getListMenu(uiState: SupplierDetailUiState): List<Menu> {
    val menu = listOf(Menu.SEARCH, Menu.FILTER, Menu.OTHER)

    return menu
}