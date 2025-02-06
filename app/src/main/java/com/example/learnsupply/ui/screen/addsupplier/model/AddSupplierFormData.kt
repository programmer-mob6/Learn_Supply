package com.example.learnsupply.ui.screen.addsupplier.model

data class AddSupplierFormData(
    val companyName: String = "",
    val suppliedItem: List<AddSupplierSuppliedItem> = listOf(),
    val country: String = "",
    val state: String = "",
    val city: String = "",
    val zip: Int = 0,
    val companyAddress: String = "",
    val companyNumber: String = "",
    val picName: String = "",
    val picNumber: String = "",
    val picEmail: String = "",
)

data class AddSupplierSuppliedItem(
    val id: Int = 0,
    val itemName: String = "",
    val itemSku: List<String> = listOf(),
)
