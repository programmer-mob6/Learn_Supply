package com.example.learnsupply.ui.screen.detailsupplier.view

import androidx.compose.runtime.Composable
import com.example.learnsupply.ui.screen.detailsupplier.model.DetailSupplierTab
import com.example.learnsupply.ui.screen.detailsupplier.view.tab.SupplierActivitiesDetailAppBar
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar

@Composable
fun SupplierDetailTopAppBar(
    itemId: String,
    navigateUp: () -> Unit,
    onShowSnackbar: OnShowSnackBar,
    curTab: DetailSupplierTab
) {
    when (curTab) {
        DetailSupplierTab.SUPPLIER_ACTIVITIES -> {
            SupplierActivitiesDetailAppBar(
                itemId = itemId,
                navigateUp = navigateUp,
                onShowSnackbar = onShowSnackbar
            )
        }
    }
}

