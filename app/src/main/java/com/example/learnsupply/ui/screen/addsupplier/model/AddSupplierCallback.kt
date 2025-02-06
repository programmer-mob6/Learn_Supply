package com.example.learnsupply.ui.screen.addsupplier.model

data class AddSupplierCallback(
    val onClearField: () -> Unit = {},
    val onResetMessageState: () -> Unit = {},
    val onUpdateFormData: (AddSupplierFormData) -> Unit = { },
    val onSubmitForm: () -> Unit = {},
    val onUpdateStayOnForm: () -> Unit = {},

    val onAddingSuppliedItem: () -> Unit = {},
    val removingSuppliedItemById: (Int) -> Unit = {},
    val onUpdateListSuppliedItem: (AddSupplierSuppliedItem) -> Unit = {}
)
