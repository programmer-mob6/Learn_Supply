package com.example.learnsupply.ui.screen.addsupplier.model

import com.tagsamurai.tscomponents.button.OptionData

data class AddSupplierFormOption(
    val itemName: List<OptionData<String>> = emptyList(),
    val itemSku: List<OptionData<String>> = emptyList(),
    val country: List<OptionData<String>> = emptyList(),
    val state: List<OptionData<String>> = emptyList(),
    val city: List<OptionData<String>> = emptyList()
)
