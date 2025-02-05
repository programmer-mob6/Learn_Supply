package com.example.learnsupply.ui.screen.changelog.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learnsupply.ui.screen.changelog.model.ChangeLogCallback
import com.example.learnsupply.ui.screen.changelog.uistate.ChangeLogUiState
import com.example.learnsupply.ui.screen.changelog.view.listsection.ChangeLogListSection
import com.example.learnsupply.ui.screen.changelog.viewmodel.ChangeLogViewModel
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

    // handle state buat download

    ChangeLogListSection(
        uiState = uiState,
        cb = cb
    )
}