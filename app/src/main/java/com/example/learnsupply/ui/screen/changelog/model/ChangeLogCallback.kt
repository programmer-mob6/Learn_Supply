package com.example.learnsupply.ui.screen.changelog.model

import com.example.learnsupply.model.changelog.ChangeLogFilterData

data class ChangeLogCallback(
    val onFilter: (ChangeLogFilterData) -> Unit = {},
    val onSearch: (String) -> Unit = {},
    val onRefresh: () -> Unit = {},
    val onDownload: (String) -> Unit = {},
    val onResetMessageState: () -> Unit = {}
)
