package com.example.learnsupply.ui.screen.supplier.view.tab.list.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.ui.screen.supplier.component.ActivateItemDialog
import com.example.learnsupply.ui.screen.supplier.component.DeleteItemDialog
import com.example.learnsupply.ui.screen.supplier.view.SupplierFilterSheet
import com.example.learnsupply.ui.screen.supplier.view.tab.list.model.SupplierListCallback
import com.example.learnsupply.ui.screen.supplier.view.tab.list.uistate.SupplierListUiState
import com.example.learnsupply.ui.screen.supplier.view.tab.list.viewmodel.SupplierListViewModel
import com.tagsamurai.tscomponents.alertdialog.DownloadConfirmDialog
import com.tagsamurai.tscomponents.model.Menu
import com.tagsamurai.tscomponents.textfield.SearchFieldTopAppBar
import com.tagsamurai.tscomponents.topappbar.TopAppBar
import com.tagsamurai.tscomponents.utils.Utils.getDownloadFilename

@Composable
fun SupplierListTopAppBar(
    navigateUp: () -> Unit,
    navigateTo: (String) -> Unit,
) {
    val supplierListViewModel: SupplierListViewModel = hiltViewModel()
    val uiState by supplierListViewModel.uiState.collectAsStateWithLifecycle()

    val cb = supplierListViewModel.getCallback()

    SupplierListTopAppBar(
        uiState = uiState,
        navigateUp = {
            if (uiState.itemSelected.isNotEmpty())
                cb.onResetSelect()
            else navigateUp()
        },
        cb = cb,
        navigateTo = navigateTo
    )
}

@Composable
private fun SupplierListTopAppBar(
    uiState: SupplierListUiState,
    navigateUp: () -> Unit,
    cb: SupplierListCallback,
    navigateTo: (String) -> Unit,
) {
    var showSearch by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var showActionSheet by remember { mutableStateOf(false) }
    var showDownloadDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showActiveDialog by remember { mutableStateOf(false) }
    var navigateToChangelog by remember { mutableStateOf(false) }
    var isActivate: Boolean? by remember { mutableStateOf(null) }

    val listMenu = getListMenu(uiState)

    if (showSearch) {
        SearchFieldTopAppBar(
            onNavigateUp = { showSearch = false },
            onSearchConfirm = cb.onSearch
        )
    } else {
        TopAppBar(
            menu = listMenu,
            canNavigateBack = false,
            onMenuAction = { menu ->
                when (menu) {
                    Menu.SEARCH -> showSearch = true
                    Menu.FILTER -> showFilterSheet = true
                    Menu.DOWNLOAD -> showDownloadDialog = true
//                Menu.LOG -> navigateToChangelog = true
                    Menu.SELECT_ALL, Menu.UNSELECT_ALL -> cb.onToggleSelectAll()
                    Menu.OTHER -> showActionSheet = true
                    else -> Unit
                }
            },
            title = if (uiState.itemSelected.isNotEmpty()) "${uiState.itemSelected.size}" else "",
            navigateUp = {},
        )
    }

    SupplierFilterSheet(
        onDismissRequest = { state -> showFilterSheet = state },
        uiState = uiState,
        showFilter = showFilterSheet,
        onApplyConfirm = cb.onFilter
    )

    SupplierActionSheet(
        onDismissRequest = { showActionSheet = it },
        uiState = uiState,
        item = SupplierEntity(),
        showSheet = showActionSheet,
        onDelete = { showDeleteDialog = true },
        onUpdateActive = {
            isActivate = it
            showActiveDialog = true
        }
    )

    DeleteItemDialog(
        onDismissRequest = { showDeleteDialog = it },
        supplier = uiState.itemSelected,
        showDialog = showDeleteDialog,
        onConfirm = { value ->
            showActionSheet = false
            cb.onDeleteAssetById(value.map { it.id })
        }
    )
    isActivate?.let { status ->
        ActivateItemDialog(
            onDismissRequest = {
                showActiveDialog = it
                isActivate = null
            },
            supplier = uiState.itemSelected,
            isActive = status,
            showDialog = showActiveDialog,
            onConfirm = { value ->
                cb.onUpdateActiveById(value.map { it.id }, status)
                showActionSheet = false
            }
        )
    }


    if (showDownloadDialog) {
        val filename = "Supplier-List".getDownloadFilename()
        DownloadConfirmDialog(
            filename = filename,
            onDismissRequest = { showDownloadDialog = false },
            onConfirm = { cb.onDownload(filename) },
        )
    }
}

fun getListMenu(uiState: SupplierListUiState): List<Menu> {
    val selectingMenu = listOf(
        Menu.SEARCH,
        if (uiState.isAllSelected) Menu.SELECT_ALL else Menu.UNSELECT_ALL,
        Menu.OTHER
    )
    val notSelectingMenu = listOf(Menu.SEARCH, Menu.FILTER, Menu.DOWNLOAD, Menu.LOG)
    return if (uiState.itemSelected.isNotEmpty()) selectingMenu else notSelectingMenu
}
