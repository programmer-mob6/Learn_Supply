package com.example.learnsupply.ui.screen.changelog.uistate

import com.example.apiservices.data.model.supplier.ChangeLogEntity
import com.example.learnsupply.model.changelog.ChangeLogFilterData
import com.example.learnsupply.model.changelog.ChangeLogFilterOption
import com.google.common.truth.Truth
import com.tagsamurai.tscomponents.button.OptionData
import com.tagsamurai.tscomponents.utils.Utils
import org.junit.Test

class ChangeLogUiStateTest {
    @Test
    fun `constructor should return default value`() {
        val uiState = ChangeLogUiState()

        Truth.assertThat(uiState.isLoading).isFalse()
        Truth.assertThat(uiState.isLoadingOverlay).isFalse()
        Truth.assertThat(uiState.searchQuery).isEmpty()
        Truth.assertThat(uiState.filterOption).isEqualTo(ChangeLogFilterOption())
        Truth.assertThat(uiState.filterData).isEqualTo(ChangeLogFilterData())
        Truth.assertThat(uiState.downloadState).isNull()
        Truth.assertThat(uiState.item).isEmpty()
    }

    @Test
    fun `constructor should return custom value`() {
        val uiState = ChangeLogUiState(
            isLoading = true,
            isLoadingOverlay = true,
            searchQuery = "search",
            filterOption = ChangeLogFilterOption(
                actionSelected = listOf(
                    OptionData(
                        "action",
                        "action"
                    )
                )
            ),
            filterData = ChangeLogFilterData(actionSelected = listOf("action")),
            downloadState = true,
            item = listOf(ChangeLogEntity(action = "action"))
        )

        Truth.assertThat(uiState.isLoading).isTrue()
        Truth.assertThat(uiState.isLoadingOverlay).isTrue()
        Truth.assertThat(uiState.searchQuery).isEqualTo("search")
        Truth.assertThat(uiState.filterOption).isEqualTo(
            ChangeLogFilterOption(
                actionSelected = listOf(
                    OptionData(
                        "action",
                        "action"
                    )
                )
            )
        )
        Truth.assertThat(uiState.filterData)
            .isEqualTo(ChangeLogFilterData(actionSelected = listOf("action")))
        Truth.assertThat(uiState.downloadState).isTrue()
        Truth.assertThat(uiState.item).isEqualTo(listOf(ChangeLogEntity(action = "action")))
    }

    @Test
    fun `constructor should return custom value when downloadState false`() {
        val uiState = ChangeLogUiState(
            isLoading = true,
            isLoadingOverlay = true,
            searchQuery = "search",
            filterOption = ChangeLogFilterOption(
                actionSelected = listOf(
                    OptionData(
                        "action",
                        "action"
                    )
                )
            ),
            filterData = ChangeLogFilterData(actionSelected = listOf("action")),
            downloadState = false,
            item = listOf(ChangeLogEntity(action = "action"))
        )

        Truth.assertThat(uiState.isLoading).isTrue()
        Truth.assertThat(uiState.isLoadingOverlay).isTrue()
        Truth.assertThat(uiState.searchQuery).isEqualTo("search")
        Truth.assertThat(uiState.filterOption).isEqualTo(
            ChangeLogFilterOption(
                actionSelected = listOf(
                    OptionData(
                        "action",
                        "action"
                    )
                )
            )
        )
        Truth.assertThat(uiState.filterData)
            .isEqualTo(ChangeLogFilterData(actionSelected = listOf("action")))
        Truth.assertThat(uiState.downloadState).isFalse()
        Truth.assertThat(uiState.item).isEqualTo(listOf(ChangeLogEntity(action = "action")))
    }

    @Test
    fun `queryParams should return null when searchQuery is blank`() {
        // Arrange
        val uiState = ChangeLogUiState(searchQuery = "   ")

        // Act
        val queryParams = uiState.queryParams

        // Assert
        Truth.assertThat(queryParams.search).isNull()
    }

    @Test
    fun `queryParams should return correct GetChangeLogQueryParams`() {
        // Arrange
        val uiState = ChangeLogUiState(
            searchQuery = "ChangeLog Test",
            filterData = ChangeLogFilterData(
                actionSelected = listOf("Action1"),
                fieldSelected = listOf("Field1"),
                modifiedBySelected = listOf("User1"),
                date = listOf(123456789)
            )
        )

        // Act
        val queryParams = uiState.queryParams

        // Assert
        Truth.assertThat(queryParams.search).isEqualTo("ChangeLog Test")
        Truth.assertThat(queryParams.action).isEqualTo(Utils.toJsonIfNotEmpty(listOf("Action1")))
        Truth.assertThat(queryParams.field).isEqualTo(Utils.toJsonIfNotEmpty(listOf("Field1")))
        Truth.assertThat(queryParams.modifiedBy).isEqualTo(Utils.toJsonIfNotEmpty(listOf("User1")))
        Truth.assertThat(queryParams.date).isEqualTo(Utils.toJsonIfNotEmpty(listOf(123456789)))
    }
}