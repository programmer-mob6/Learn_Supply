package com.example.learnsupply.ui.screen.supplier.view

import androidx.compose.runtime.Composable
import com.example.learnsupply.ui.screen.supplier.view.tab.activies.SupplierActivitiesTopAppBar
import com.example.learnsupply.ui.screen.supplier.view.tab.list.view.SupplierListTopAppBar

@Composable
fun SupplierTopAppBar(
    tabIndex: Int,
    navigateUp: () -> Unit,
    navigateTo: (String) -> Unit,
) {
    when (tabIndex) {
        0 -> SupplierListTopAppBar(
            navigateUp = navigateUp,
            navigateTo = navigateTo
        )

        1 -> SupplierActivitiesTopAppBar(
            navigateUp = navigateUp,
            navigateTo = navigateTo
        )
    }
}