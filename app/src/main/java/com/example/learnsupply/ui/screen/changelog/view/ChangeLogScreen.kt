package com.example.learnsupply.ui.screen.changelog.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.learnsupply.R
import com.example.learnsupply.ui.screen.supplier.view.SupplierTopAppBar
import com.tagsamurai.tscomponents.alertdialog.DownloadConfirmDialog
import com.tagsamurai.tscomponents.pagetitle.PageTitle
import com.tagsamurai.tscomponents.scaffold.Scaffold
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar
import com.tagsamurai.tscomponents.utils.Utils.getDownloadFilename

@Composable
fun ChangeLogScreen(
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

    ChangeLogScreen(
        navigateUp = { navController.popBackStack() },
        onShowSnackbar = onShowSnackBar,
//        navigateTo = { route -> navController.navigate(route) }
    )
}

@Composable
private fun ChangeLogScreen(
    navigateUp: () -> Unit,
    onShowSnackbar: OnShowSnackBar,
//    navigateTo: (String) -> Unit
) {
    Scaffold(
        topBar = {
            ChangeLogTopAppBar(
                navigateUp = navigateUp,
            )
        }
    ) {
        Column {
            PageTitle(title = stringResource(R.string.title_change_log_supplier))
            Box(modifier = Modifier.weight(1f)) {
                ChangelogContent(
                    onShowSnackBar = onShowSnackbar
                )
            }

        }

    }


}