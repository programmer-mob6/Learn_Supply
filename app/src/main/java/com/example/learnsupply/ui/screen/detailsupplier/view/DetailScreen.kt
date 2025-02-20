package com.example.learnsupply.ui.screen.detailsupplier.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.learnsupply.R
import com.example.learnsupply.ui.screen.detailsupplier.model.DetailSupplierTab
import com.example.learnsupply.ui.screen.detailsupplier.uistate.SupplierDetailUiState
import com.example.learnsupply.ui.screen.detailsupplier.viewmodel.SupplierDetailViewModel
import com.tagsamurai.tscomponents.pagetitle.PageTitle
import com.tagsamurai.tscomponents.scaffold.Scaffold
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar
import com.tagsamurai.tscomponents.theme.LocalTheme
import com.tagsamurai.tscomponents.theme.SP12
import com.tagsamurai.tscomponents.theme.W600

@Composable
fun DetailScreen(
    itemId: String,
    navController: NavHostController,
    onShowSnackBar: OnShowSnackBar
) {
    val viewModel: SupplierDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getDetailSupplier()
    }

    DetailHomeScreen(
        itemId = itemId,
        uiState = uiState,
        navigateUp = { navController.popBackStack() },
        onShowSnackbar = onShowSnackBar,
        onChangeCurTab = viewModel::changeCurTab
    )
}

@Composable
private fun DetailHomeScreen(
    itemId: String,
    uiState: SupplierDetailUiState,
    navigateUp: () -> Unit,
    onShowSnackbar: OnShowSnackBar,
    onChangeCurTab: (Int) -> Unit,
) {

    var showDetail by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SupplierDetailTopAppBar(
                itemId = itemId,
                navigateUp = navigateUp,
                onShowSnackbar = onShowSnackbar,
                curTab = uiState.curTab
            )
        }
    ) {
        Column {
            PageTitle(title = stringResource(R.string.title_supplier_detail))
            SupplierDetailHeader(
                uiState = uiState,
                onClickAction = {
                    showDetail = true
                }
            )

            SupplierDetailTabRow(
                uiState = uiState,
                onChangeTab = { onChangeCurTab(it) },
            )

            Box(modifier = Modifier.weight(1f)) {
                DetailSupplierContent()
            }
        }

        SupplierDetailBottomSheet(
            supplierDetail = uiState.supplierDetail,
            showSheet = showDetail,
        ) {
            showDetail = false
        }
    }
}

@Composable
private fun SupplierDetailTabRow(
    uiState: SupplierDetailUiState,
    onChangeTab: (Int) -> Unit,
) {
    val theme = LocalTheme.current
    val tabs = DetailSupplierTab.entries

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    with(uiState) {
        ScrollableTabRow(
            modifier = Modifier.padding(horizontal = 12.dp),
            selectedTabIndex = curTabIdx,
            containerColor = theme.bodyBackground,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[curTabIdx]),
                    height = 1.dp,
                    color = theme.primary
                )
            },
            divider = { HorizontalDivider(color = theme.lineStroke) },
            edgePadding = 0.dp
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selectedContentColor = theme.primary,
                    unselectedContentColor = theme.general200,
                    text = {
                        Text(
                            text = stringResource(id = tab.label),
                            style = SP12.W600,
                            modifier = Modifier.padding(horizontal = (screenWidth / (tabs.size * 1)))
                        )
                    },
                    selected = tab == curTab,
                    onClick = {
                        onChangeTab(index)
                    },
                )
            }
        }
    }
}