package com.example.learnsupply.ui.screen.addsupplier.model

data class AddSupplierFormError(
    val companyName: String? = null,
    val itemName: String? = null,
    val itemSku: String? = null,
    val country: String? = null,
    val state: String? = null,
    val city: String? = null,
    val zip: String? = null,
    val companyAddress: String? = null,
    val companyNumber: String? = null,
    val picName: String? = null,
    val picNumber: String? = null,
    val picEmail: String? = null
) {
    fun hasError(): Boolean {
        return companyName != null ||
                itemName != null ||
                itemSku != null ||
                country != null ||
                state != null ||
                city != null ||
                zip != null ||
                companyAddress != null ||
                companyNumber != null ||
                picName != null ||
                picNumber != null ||
                picEmail != null

    }
}
