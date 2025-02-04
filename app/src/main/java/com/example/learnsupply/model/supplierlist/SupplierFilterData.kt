package com.example.learnsupply.model.supplierlist

import com.tagsamurai.tscomponents.model.TreeNode

data class SupplierFilterData(
    val activeSelected: List<String> = emptyList(),
    val supplierSelected: List<String> = emptyList(),
    val citySelected: List<String> = emptyList(),
    val itemNameSelected: List<String> = emptyList(),
    val itemSkuSelected: List<String> = emptyList(),
    val modifiedBySelected: List<String> = emptyList(),
    val date: List<Long> = emptyList(),

    val group: List<TreeNode<String>> = emptyList(),
)
