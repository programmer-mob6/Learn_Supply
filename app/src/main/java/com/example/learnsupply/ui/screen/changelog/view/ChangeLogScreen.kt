package com.example.learnsupply.ui.screen.changelog.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.learnsupply.R
import com.tagsamurai.tscomponents.pagetitle.PageTitle
import com.tagsamurai.tscomponents.scaffold.Scaffold
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar

@Composable
fun ChangeLogScreen(
    navController: NavHostController,
    onShowSnackBar: OnShowSnackBar
) {
    ChangeLogScreen(
        navigateUp = { navController.popBackStack() },
        onShowSnackbar = onShowSnackBar,
    )
}

@Composable
private fun ChangeLogScreen(
    navigateUp: () -> Unit,
    onShowSnackbar: OnShowSnackBar,
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