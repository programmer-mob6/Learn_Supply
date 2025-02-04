package com.example.learnsupply.ui.screen.detailsupplier.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.learnsupply.R
import com.example.learnsupply.model.supplierlist.SupplierFilterData
import com.example.learnsupply.ui.screen.detailsupplier.uistate.SupplierDetailUiState
import com.tagsamurai.tscomponents.bottomsheet.FilterBottomSheet
import com.tagsamurai.tscomponents.bottomsheet.TreeMultiSelectBottomSheet
import com.tagsamurai.tscomponents.chip.ChipSelectorWithOptionData
import com.tagsamurai.tscomponents.datepicker.FilterDatePicker

@Composable
fun SupplierFilterDetailSheet(
    onDismissRequest: (Boolean) -> Unit,
    uiState: SupplierDetailUiState,
    showFilter: Boolean,
    onApplyConfirm: (SupplierFilterData) -> Unit
) {
    var tempFilterData by remember { mutableStateOf(uiState.filterData) }
    val filterOption = uiState.filterOption


    LaunchedEffect(uiState.filterData) {
        if (uiState.filterData != tempFilterData) {
            tempFilterData = uiState.filterData
        }
    }

    FilterBottomSheet(
        onDismissRequest = {
            tempFilterData = uiState.filterData
            onDismissRequest(false)
        },
        onApplyConfirm = {
            onApplyConfirm(tempFilterData)
            onDismissRequest(false)
        },
        onResetConfirm = {
            tempFilterData = SupplierFilterData()
        },
        isItemSelected = tempFilterData != SupplierFilterData(),
        isShowSheet = showFilter
    ) { reset ->
        ChipSelectorWithOptionData(
            title = stringResource(R.string.transaction),
            value = tempFilterData.activeSelected,
            isReset = reset,
            items = uiState.filterOption.activeSelected,
            onChipsSelected = { result ->
                tempFilterData = tempFilterData.copy(activeSelected = result)
            }
        )
        TreeMultiSelectBottomSheet(
            title = stringResource(id = R.string.group),
            nodes = filterOption.groupOption,
            value = tempFilterData.group,
            refreshing = filterOption.groupOption.isEmpty(),
            isReset = reset,
            isCategory = false,
            onResult = {
                tempFilterData = tempFilterData.copy(group = it)
            }
        )
        ChipSelectorWithOptionData(
            title = stringResource(R.string.pic),
            value = tempFilterData.supplierSelected,
            isReset = reset,
            items = uiState.filterOption.supplierSelected,
            onChipsSelected = { result ->
                tempFilterData = tempFilterData.copy(supplierSelected = result)
            }
        )
        FilterDatePicker(
            title = stringResource(R.string.title_last_modified),
            value = tempFilterData.date,
            isReset = reset,
            onApplyConfirm = { from, to ->
                val modifiedDate = if (listOf(from, to).contains(0L)) {
                    emptyList()
                } else listOf(from, to)
                tempFilterData = tempFilterData.copy(date = modifiedDate)
            }
        )
    }
}