package com.example.learnsupply.model.changelog

import com.tagsamurai.tscomponents.button.OptionData

data class ChangeLogFilterOption(
    val date: List<Long> = emptyList(),
    val actionSelected: List<OptionData<String>> = emptyList(),
    val fieldSelected: List<OptionData<String>> = emptyList(),
    val modifiedBySelected: List<OptionData<String>> = emptyList()
)
