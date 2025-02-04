package com.example.learnsupply.ui.screen.addsupplier.model

data class AddSupplierCallback(
    val onClearField: () -> Unit = {},
    val onResetMessageState: () -> Unit = {},
    val onUpdateFormData: (AddSupplierFormData) -> Unit = { }, //, AddSupplierSuppliedItem
    val onSubmitForm: () -> Unit = {},
    val onUpdateStayOnForm: () -> Unit = {},

    val onAddingSuppliedItem: () -> Unit = {}, //AddSupplierSuppliedItem
    val removingSuppliedItemById: (Int) -> Unit = {}, //AddSupplierSuppliedItem
    val onUpdateListSuppliedItem: (AddSupplierSuppliedItem) -> Unit = {}
)
