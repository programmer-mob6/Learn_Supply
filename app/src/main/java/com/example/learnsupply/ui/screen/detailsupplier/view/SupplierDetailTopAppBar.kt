package com.example.learnsupply.ui.screen.detailsupplier.view

import androidx.compose.runtime.Composable
import com.example.learnsupply.ui.screen.detailsupplier.model.DetailSupplierTab
import com.example.learnsupply.ui.screen.detailsupplier.view.tab.SupplierActivitiesDetailAppBar

@Composable
fun SupplierDetailTopAppBar(
    navigateUp: () -> Unit,
    curTab: DetailSupplierTab
) {
    when(curTab) {
        DetailSupplierTab.SUPPLIER_ACTIVITIES -> {
            SupplierActivitiesDetailAppBar(
                navigateUp = navigateUp
            )
        }
    }
}

