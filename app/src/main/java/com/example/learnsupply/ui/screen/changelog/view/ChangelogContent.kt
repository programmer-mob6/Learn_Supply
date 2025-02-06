package com.example.learnsupply.ui.screen.changelog.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.learnsupply.R
import com.example.learnsupply.ui.screen.changelog.view.listsection.ChangeLogListSection
import com.example.learnsupply.ui.screen.changelog.viewmodel.ChangeLogViewModel
import com.tagsamurai.tscomponents.handlestate.HandleState
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar

@Composable
fun ChangelogContent(
    onShowSnackBar: OnShowSnackBar
) {
    val changeLogViewModel: ChangeLogViewModel = hiltViewModel()
    val uiState by changeLogViewModel.uiState.collectAsStateWithLifecycle()

    val cb = changeLogViewModel.getCallback()

    LaunchedEffect(Unit) {
        changeLogViewModel.init()
    }

    HandleState(
        state = uiState.downloadState,
        onShowSnackBar = onShowSnackBar,
        successMsg = stringResource(R.string.message_success_delete_supplier),
        errorMsg = stringResource(R.string.message_error_delete_supplier),
        onDispose = cb.onResetMessageState
    )

    ChangeLogListSection(
        uiState = uiState,
        cb = cb
    )
}