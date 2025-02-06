package com.example.learnsupply.ui.screen.supplier.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.learnsupply.model.SupllierTabEnum
import com.example.learnsupply.ui.screen.supplier.uistate.SupplierUiState
import com.example.learnsupply.ui.screen.supplier.view.tab.activies.SupplierActivitiesContent
import com.example.learnsupply.ui.screen.supplier.view.tab.list.view.SupplierListContent
import com.tagsamurai.tscomponents.pagetitle.PageTitle
import com.tagsamurai.tscomponents.tab.TabList

@Composable
fun SupplierContent(
    uiState: SupplierUiState,
    onTabChange: (Int) -> Unit,
    navigateTo: (String) -> Unit,
    onShowSnackbar: (String, Boolean) -> Unit,
) {
    val tabList = SupllierTabEnum.entries.map { it.title }

    Column {
        PageTitle(
            title = stringResource(com.example.learnsupply.R.string.title_home),
            bottomContent = {
                TabList(
                    onTabChange = onTabChange,
                    tabs = tabList,
                    selectedTabIndex = uiState.curTabIdx,
                )
            }
        )
        when (uiState.curTab) {
            SupllierTabEnum.LIST -> {
                SupplierListContent(
                    navigateTo = navigateTo,
                    onShowSnackBar = onShowSnackbar,
                )
            }

            SupllierTabEnum.ACTIVITIES -> {
                SupplierActivitiesContent()
            }
        }

    }
}