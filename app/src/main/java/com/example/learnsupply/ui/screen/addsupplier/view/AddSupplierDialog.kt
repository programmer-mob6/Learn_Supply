package com.example.learnsupply.ui.screen.addsupplier.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierCallback
import com.example.learnsupply.ui.screen.addsupplier.uistate.AddSuplierUiState
import com.example.learnsupply.ui.screen.addsupplier.viewmodel.AddSupplierViewModel
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar
import com.tagsamurai.tscomponents.snackbar.Snackbar
import com.tagsamurai.tscomponents.snackbar.showSnackbar

@Composable
fun AddSupplierDialog(
    onDismissRequest: (Boolean) -> Unit,
    showDialog: Boolean,
    id: String? = null,
    onShowSnackBar: OnShowSnackBar,
    onSuccess: () -> Unit
) {
    val viewModel: AddSupplierViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val callback = viewModel.getCallback()

    LaunchedEffect(showDialog) {
        if (showDialog) {
            viewModel.init(id = id)
        }
    }

    AddSupplierDialog(
        showDialog = showDialog,
        uiState = uiState,
        callback = callback,
        onNavigateUp = { onDismissRequest(false) },
        onShowSnackBar = onShowSnackBar,
        onSuccess = onSuccess
    )
}

@Composable
private fun AddSupplierDialog(
    showDialog: Boolean,
    uiState: AddSuplierUiState,
    callback: AddSupplierCallback,
    onNavigateUp: () -> Unit,
    onShowSnackBar: OnShowSnackBar,
    onSuccess: () -> Unit
) {
    val properties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = false,
        usePlatformDefaultWidth = false
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    fun showSnackBar(message: String, isSuccess: Boolean) {
        snackbarHostState.showSnackbar(scope = scope, message = message, isSuccess = isSuccess)
    }

    if (showDialog) {
        Dialog(onDismissRequest = onNavigateUp, properties = properties) {
            (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0f)
            Box {
                AddSuplierScreen(
                    uiState = uiState,
                    callback = callback,
                    onNavigateUp = onNavigateUp,
                    onShowSnackBar = { message, isSuccess ->
                        onShowSnackBar(message, isSuccess)
                        if (!isSuccess || uiState.isStayOnForm) {
                            showSnackBar(message, isSuccess)
                        }
                    },
                    onSuccess = onSuccess
                )
                Snackbar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}
