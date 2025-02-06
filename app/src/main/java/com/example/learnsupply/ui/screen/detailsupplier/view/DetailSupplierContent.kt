package com.example.learnsupply.ui.screen.detailsupplier.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.learnsupply.ui.screen.detailsupplier.model.DetailSupplierTab
import com.example.learnsupply.ui.screen.detailsupplier.viewmodel.SupplierDetailViewModel
import com.example.learnsupply.ui.screen.supplier.view.tab.activies.SupplierActivitiesContent

@Composable
fun DetailSupplierContent(
    viewModel: SupplierDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(
        initialPage = uiState.curTabIdx,
        pageCount = {
            DetailSupplierTab.entries.size
        },
    )
    LaunchedEffect(uiState.curTabIdx) {
        pagerState.animateScrollToPage(uiState.curTabIdx)
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.changeCurTab(pagerState.currentPage)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
        ) {
            when (it) {
                0 -> {
                    SupplierActivitiesContent()
                }
            }
        }
    }
}