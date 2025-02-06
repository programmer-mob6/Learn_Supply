package com.example.learnsupply.model.changelog

data class ChangeLogFilterData(
    val date: List<Long> = emptyList(),
    val actionSelected: List<String> = emptyList(),
    val fieldSelected: List<String> = emptyList(),
    val modifiedBySelected: List<String> = emptyList(),
)
