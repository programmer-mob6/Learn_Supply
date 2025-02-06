package com.example.learnsupply.ui.screen.detailsupplier.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.learnsupply.R
import com.example.learnsupply.model.supplierdetail.SupplierDetailFilterData
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
    onApplyConfirm: (SupplierDetailFilterData) -> Unit
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
            tempFilterData = SupplierDetailFilterData()
        },
        isItemSelected = tempFilterData != SupplierDetailFilterData(),
        isShowSheet = showFilter
    ) { reset ->
        ChipSelectorWithOptionData(
            title = stringResource(R.string.transaction),
            value = tempFilterData.transactionSelected,
            isReset = reset,
            items = uiState.filterOption.transactionSelected,
            onChipsSelected = { result ->
                tempFilterData = tempFilterData.copy(transactionSelected = result)
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
            value = tempFilterData.picSelected,
            isReset = reset,
            items = uiState.filterOption.picSelected,
            onChipsSelected = { result ->
                tempFilterData = tempFilterData.copy(picSelected = result)
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