package com.example.learnsupply.model.supplierlist

import com.tagsamurai.tscomponents.button.OptionData

data class SupplierFilterOption(
    val activeSelected: List<OptionData<Boolean>> = emptyList(),
    val supplierSelected: List<OptionData<String>> = emptyList(),
    val citySelected: List<OptionData<String>> = emptyList(),
    val itemNameSelected: List<OptionData<String>> = emptyList(),
    val modifiedBySelected: List<OptionData<String>> = emptyList()
)
