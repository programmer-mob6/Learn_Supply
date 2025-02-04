package com.example.learnsupply.ui.screen.supplier.view.tab.list.view.listsection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.ui.screen.supplier.view.tab.list.model.SupplierListCallback
import com.example.learnsupply.ui.screen.supplier.view.tab.list.uistate.SupplierListUiState
import com.tagsamurai.tscomponents.loading.LoadingOverlay
import com.tagsamurai.tscomponents.pullrefresh.PullRefresh
import com.tagsamurai.tscomponents.screen.EmptyState
import com.tagsamurai.tscomponents.utils.Spacer.heightBox
import com.tagsamurai.tscomponents.utils.paddingList

@Composable
fun SupplierListSection(
    uiState: SupplierListUiState,
    supplierCallback: SupplierListCallback,
    onNavigateTo: (String) -> Unit,
    onEditAsset: (SupplierEntity) -> Unit
) {
    when {
        uiState.isLoading -> {
            Column(modifier = Modifier.padding(paddingList)) {
                repeat(5) {
                    SupplierLoadingItem()
                    4.heightBox()
                }
            }
        }

        else -> {
            if (uiState.item.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    EmptyState()
                }
            } else {
                PullRefresh(onRefresh = supplierCallback.onRefresh) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = paddingList,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(uiState.item.size) { index ->
                            SupplierItem(
                                uiState = uiState,
                                item = uiState.item[index],
                                cb = supplierCallback,
                                onNavigateTo = onNavigateTo,
                                onEditAsset = onEditAsset
                            )
                        }
                    }
                }
            }
        }
    }
    LoadingOverlay(uiState.isLoadingOverlay)
}