package com.example.learnsupply.ui.screen.addsupplier.uistate

import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierFormData
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierFormError
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierFormOption

data class AddSuplierUiState(
    val isLoadingOverlay: Boolean = false,
    val isLoadingFormOption: Boolean = false,
    val isEditForm: Boolean = false,
    val isStayOnForm: Boolean = false,
    val assetId: String = "",
    val formData: AddSupplierFormData = AddSupplierFormData(),
    val formError: AddSupplierFormError = AddSupplierFormError(),
    val formOption: AddSupplierFormOption = AddSupplierFormOption(),
    val submitState: Boolean? = null,

    val suppliedItemIndex: Int = 0
)
