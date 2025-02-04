package com.example.learnsupply.ui.screen.supplier.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.learnsupply.R
import com.example.learnsupply.model.supplierlist.SupplierFilterData
import com.example.learnsupply.ui.screen.supplier.view.tab.list.uistate.SupplierListUiState
import com.tagsamurai.tscomponents.bottomsheet.FilterBottomSheet
import com.tagsamurai.tscomponents.chip.ChipSelectorWithOptionData
import com.tagsamurai.tscomponents.datepicker.FilterDatePicker

@Composable
fun SupplierFilterSheet(
    onDismissRequest: (Boolean) -> Unit,
    uiState: SupplierListUiState,
    showFilter: Boolean,
    onApplyConfirm: (SupplierFilterData) -> Unit
) {
    var tempFilterData by remember { mutableStateOf(uiState.filterData) }

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
            title = stringResource(R.string.title_active),
            value = tempFilterData.activeSelected,
            isReset = reset,
            items = uiState.filterOption.activeSelected,
            onChipsSelected = { result ->
                tempFilterData = tempFilterData.copy(activeSelected = result)
            }
        )
        ChipSelectorWithOptionData(
            title = stringResource(R.string.title_supplier),
            value = tempFilterData.supplierSelected,
            isReset = reset,
            items = uiState.filterOption.supplierSelected,
            onChipsSelected = { result ->
                tempFilterData = tempFilterData.copy(supplierSelected = result)
            }
        )
        ChipSelectorWithOptionData(
            title = stringResource(R.string.title_city),
            value = tempFilterData.citySelected,
            isReset = reset,
            items = uiState.filterOption.citySelected,
            onChipsSelected = { result ->
                tempFilterData = tempFilterData.copy(citySelected = result)
            }
        )
        ChipSelectorWithOptionData(
            title = stringResource(R.string.title_item_name),
            value = tempFilterData.itemNameSelected,
            isReset = reset,
            items = uiState.filterOption.itemNameSelected,
            onChipsSelected = { result ->
                tempFilterData = tempFilterData.copy(itemNameSelected = result)
            }
        )
        ChipSelectorWithOptionData(
            title = stringResource(R.string.title_modified_by),
            value = tempFilterData.modifiedBySelected,
            isReset = reset,
            items = uiState.filterOption.modifiedBySelected,
            onChipsSelected = { result ->
                tempFilterData = tempFilterData.copy(modifiedBySelected = result)
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