package com.example.learnsupply.model.supplierdetail

import com.tagsamurai.tscomponents.model.TreeNode

data class SupplierDetailFilterData(
    val transactionSelected: List<String> = emptyList(),
    val group: List<TreeNode<String>> = emptyList(),
    val picSelected: List<String> = emptyList(),
    val date: List<Long> = emptyList()
)
