package com.example.learnsupply.model.supplierdetail

import com.tagsamurai.tscomponents.button.OptionData
import com.tagsamurai.tscomponents.model.TreeNode

data class SupplierDetailFilterOption(
    val transactionSelected: List<OptionData<String>> = emptyList(),
    val groupOption: List<TreeNode<String>> = emptyList(),
    val picSelected: List<OptionData<String>> = emptyList(),
)
