package com.example.learnsupply.ui.screen.addsupplier.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import com.example.learnsupply.R
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierCallback
import com.example.learnsupply.ui.screen.addsupplier.uistate.AddSuplierUiState
import com.tagsamurai.tscomponents.button.SingleActionButton
import com.tagsamurai.tscomponents.checkbox.CustomCheckbox
import com.tagsamurai.tscomponents.handlestate.HandleState
import com.tagsamurai.tscomponents.pagetitle.PageTitle
import com.tagsamurai.tscomponents.scaffold.Scaffold
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar
import com.tagsamurai.tscomponents.theme.theme
import com.tagsamurai.tscomponents.topappbar.TopAppBar
import com.tagsamurai.tscomponents.utils.Spacer.heightBox
import com.tagsamurai.tscomponents.utils.bodyStyle

@Composable
fun AddSuplierScreen(
    uiState: AddSuplierUiState,
    callback: AddSupplierCallback,
    onNavigateUp: () -> Unit,
    onShowSnackBar: OnShowSnackBar,
    onSuccess: () -> Unit
) {
    val successMessage: String
    val errorMessage: String
    val submitText: String
    val titleText: String

    var textFieldValue by remember { mutableStateOf("") }
    var textFieldIsError by remember { mutableStateOf(false) }

    if (uiState.isEditForm) {
        successMessage = stringResource(R.string.message_success_asset_edited)
        errorMessage = stringResource(R.string.message_error_asset_edited)
        submitText = stringResource(R.string.title_edit)
        titleText = stringResource(R.string.title_edit_supplier)
    } else {
        successMessage = stringResource(R.string.message_success_asset_registered)
        errorMessage = stringResource(R.string.message_error_asset_registered)
        submitText = stringResource(R.string.title_create)
        titleText = stringResource(R.string.title_add_supplier)
    }

    HandleState(
        state = uiState.submitState,
        onShowSnackBar = onShowSnackBar,
        successMsg = successMessage,
        errorMsg = errorMessage,
        onSuccess = {
            if (!uiState.isStayOnForm) {
                onNavigateUp()
            }
            onSuccess()
        },
        onDispose = callback.onResetMessageState
    )

    Scaffold(
        topBar = {
            TopAppBar(
                menu = emptyList(),
                canNavigateBack = true,
                onMenuAction = {},
                navigateUp = onNavigateUp,
//                title = "Add Supplier"
            )
        },
        isShowLoadingOverlay = uiState.isLoadingOverlay
    ) {
        Column {
            PageTitle(title = titleText)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                AddSuplierForm(
                    uiState = uiState,
                    onUpdateForm = callback.onUpdateFormData,
                    onAddingSuppliedItem = callback.onAddingSuppliedItem,
                    removingSuppliedItemById = callback.removingSuppliedItemById,
                    onUpdateListSuppliedItem = callback.onUpdateListSuppliedItem
                )
            }
            SingleActionButton(
                onButtonConfirm = {
                    callback.onSubmitForm()
                },
                label = submitText,
                contentHeader = {
                    if (!uiState.isEditForm) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable(
                                role = Role.Checkbox,
                                onClick = callback.onUpdateStayOnForm
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.placeholder_stay_on_form_after_submitting),
                                style = bodyStyle,
                                color = theme.bodyText,
                                modifier = Modifier.weight(1f)
                            )
                            CustomCheckbox(
                                checked = uiState.isStayOnForm,
                                onCheckedChange = { callback.onUpdateStayOnForm() }
                            )
                        }
                        10.heightBox()
                    }
                }
            )
        }

    }
}