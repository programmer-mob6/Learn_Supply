package com.example.learnsupply.ui.screen.supplier.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.ui.screen.addsupplier.view.AddSupplierDialog
import com.example.learnsupply.ui.screen.supplier.view.tab.list.viewmodel.SupplierListViewModel
import com.example.learnsupply.ui.screen.supplier.viewmodel.SupplierViewModel
import com.tagsamurai.tscomponents.R
import com.tagsamurai.tscomponents.button.CustomFloatingIconButton
import com.tagsamurai.tscomponents.scaffold.Scaffold
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar
import com.tagsamurai.tscomponents.theme.theme


@Composable
fun SupplierScreen(
    navController: NavHostController,
    onShowSnackBar: OnShowSnackBar
) {
    var isResult by remember { mutableStateOf(false) }

    LaunchedEffect(navController) {
        val currentBackStackEntry = navController.currentBackStackEntry
        val savedStateHandle = currentBackStackEntry?.savedStateHandle
        val data = savedStateHandle?.get<Boolean>("key_refresh")
        isResult = data ?: false
    }

    SupplierScreen(
        navigateUp = { navController.popBackStack() },
        onShowSnackbar = onShowSnackBar,
        navigateTo = { route -> navController.navigate(route) }
    )
}

@Composable
private fun SupplierScreen(
    navigateUp: () -> Unit,
    onShowSnackbar: (String, Boolean) -> Unit,
    navigateTo: (String) -> Unit,
) {
    val supplierViewModel: SupplierViewModel = hiltViewModel()
    val supplierListViewModel: SupplierListViewModel = hiltViewModel()
    val uiState by supplierViewModel.uiState.collectAsStateWithLifecycle()
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    var data: SupplierEntity? by remember { mutableStateOf(null) }

    var showCreateDialog by remember { mutableStateOf(false) }
    LaunchedEffect(selectedTabIndex) {
        supplierViewModel.changeCurTab(selectedTabIndex)
    }

    Scaffold(
        topBar = {
            SupplierTopAppBar(
                tabIndex = selectedTabIndex,
                navigateUp = navigateUp,
                navigateTo = navigateTo,
            )
        },
        floatingActionButton = {
            CustomFloatingIconButton(
                icon = R.drawable.ic_add_fill_24dp,
                containerColor = theme.warning500,
                iconColor = theme.warning500
            ) {
                showCreateDialog = true
            }
        }
    ) {
        SupplierContent(
            uiState = uiState,
            onTabChange = { index -> selectedTabIndex = index },
            onShowSnackbar = onShowSnackbar,
            navigateTo = navigateTo,
        )
    }

    AddSupplierDialog(
        onDismissRequest = { state ->
            showCreateDialog = state
            data = null
        },
        showDialog = showCreateDialog,
        id = data?.id,
        onShowSnackBar = onShowSnackbar,
        onSuccess = supplierListViewModel.getCallback().onRefresh
    )
}