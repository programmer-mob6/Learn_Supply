package com.example.learnsupply.model.supplierlist

import com.tagsamurai.tscomponents.button.OptionData
import com.tagsamurai.tscomponents.model.TreeNode

data class SupplierFilterOption(
    val activeSelected: List<OptionData<String>> = emptyList(),
    val supplierSelected: List<OptionData<String>> = emptyList(),
    val citySelected: List<OptionData<String>> = emptyList(),
    val itemNameSelected: List<OptionData<String>> = emptyList(),
    val itemSkuSelected: List<String> = emptyList(),
    val modifiedBySelected: List<OptionData<String>> = emptyList(),

    val groupOption: List<TreeNode<String>> = emptyList()
)
