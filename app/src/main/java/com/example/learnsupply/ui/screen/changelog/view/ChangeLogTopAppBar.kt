package com.example.learnsupply.ui.screen.changelog.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.learnsupply.ui.screen.changelog.model.ChangeLogCallback
import com.example.learnsupply.ui.screen.changelog.uistate.ChangeLogUiState
import com.example.learnsupply.ui.screen.changelog.viewmodel.ChangeLogViewModel
import com.tagsamurai.tscomponents.model.Menu
import com.tagsamurai.tscomponents.textfield.SearchFieldTopAppBar
import com.tagsamurai.tscomponents.topappbar.TopAppBar

@Composable
fun ChangeLogTopAppBar(
    navigateUp: () -> Unit,
) {
    val changeLogViewModel: ChangeLogViewModel = hiltViewModel()
    val uiState by changeLogViewModel.uiState.collectAsStateWithLifecycle()

    val cb = changeLogViewModel.getCallback()

    ChangeLogTopAppBarContent(
        uiState = uiState,
        navigateUp = navigateUp,
        cb = cb
    )
}

@Composable
private fun ChangeLogTopAppBarContent(
    uiState: ChangeLogUiState,
    navigateUp: () -> Unit,
    cb: ChangeLogCallback,
){
    var showSearch by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var showDownloadDialog by remember { mutableStateOf(false) }

    val listMenu = listOf(Menu.SEARCH, Menu.FILTER, Menu.DOWNLOAD)

    if (showSearch) {
        SearchFieldTopAppBar(
            onNavigateUp = { showSearch = false },
            onSearchConfirm = {}//cb.onSearch
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
                    else -> Unit
                }
            },
            navigateUp = navigateUp
        )
    }
}
