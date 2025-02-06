package com.example.learnsupply.ui.screen.changelog.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.learnsupply.R
import com.example.learnsupply.model.changelog.ChangeLogFilterData
import com.example.learnsupply.ui.screen.changelog.uistate.ChangeLogUiState
import com.tagsamurai.tscomponents.bottomsheet.FilterBottomSheet
import com.tagsamurai.tscomponents.chip.ChipSelectorWithOptionData
import com.tagsamurai.tscomponents.datepicker.FilterDatePicker

@Composable
fun ChangeLogFilterSheet(
    onDismissRequest: (Boolean) -> Unit,
    uiState: ChangeLogUiState,
    showFilter: Boolean,
    onApplyConfirm: (ChangeLogFilterData) -> Unit
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
            tempFilterData = ChangeLogFilterData()
        },
        isItemSelected = tempFilterData != ChangeLogFilterData(),
        isShowSheet = showFilter
    ) { reset ->
        FilterDatePicker(
            title = stringResource(R.string.title_date),
            value = tempFilterData.date,
            isReset = reset,
            onApplyConfirm = { from, to ->
                val modifiedDate = if (listOf(from, to).contains(0L)) {
                    emptyList()
                } else listOf(from, to)
                tempFilterData = tempFilterData.copy(date = modifiedDate)
            }
        )
        ChipSelectorWithOptionData(
            title = stringResource(R.string.title_modified_by),
            value = tempFilterData.actionSelected,
            isReset = reset,
            items = uiState.filterOption.actionSelected,
            onChipsSelected = { result ->
                tempFilterData = tempFilterData.copy(actionSelected = result)
            }
        )
        ChipSelectorWithOptionData(
            title = stringResource(R.string.title_modified_by),
            value = tempFilterData.fieldSelected,
            isReset = reset,
            items = uiState.filterOption.fieldSelected,
            onChipsSelected = { result ->
                tempFilterData = tempFilterData.copy(fieldSelected = result)
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
    }
}