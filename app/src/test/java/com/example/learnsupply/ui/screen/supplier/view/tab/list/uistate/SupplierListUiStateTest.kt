package com.example.learnsupply.ui.screen.supplier.view.tab.list.uistate

import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.model.supplierlist.SupplierFilterData
import com.example.learnsupply.model.supplierlist.SupplierFilterOption
import com.google.common.truth.Truth
import com.tagsamurai.tscomponents.button.OptionData
import com.tagsamurai.tscomponents.utils.Utils
import org.junit.Test

class SupplierListUiStateTest {
    @Test
    fun `constructor should return default value`() {
        val uiState = SupplierListUiState()

        Truth.assertThat(uiState.isLoading).isFalse()
        Truth.assertThat(uiState.isLoadingOverlay).isFalse()
        Truth.assertThat(uiState.isAllSelected).isFalse()

        Truth.assertThat(uiState.itemSelected).isEmpty()
        Truth.assertThat(uiState.searchQuery).isEmpty()

        Truth.assertThat(uiState.filterData).isEqualTo(SupplierFilterData())
        Truth.assertThat(uiState.filterOption).isEqualTo(SupplierFilterOption())

        Truth.assertThat(uiState.item).isEmpty()

        Truth.assertThat(uiState.downloadState).isNull()
        Truth.assertThat(uiState.deleteState).isNull()
        Truth.assertThat(uiState.activateState).isNull()
        Truth.assertThat(uiState.editState).isNull()
        Truth.assertThat(uiState.activation).isNull()
    }

    @Test
    fun `constructor should return custom value`() {
        val uiState = SupplierListUiState(
            isLoading = true,
            isLoadingOverlay = true,
            isAllSelected = true,
            itemSelected = listOf(SupplierEntity(id = "id")),
            searchQuery = "search",
            filterData = SupplierFilterData(activeSelected = listOf(true)),
            filterOption = SupplierFilterOption(
                activeSelected = listOf(
                    OptionData(
                        "active",
                        true
                    )
                )
            ),
            item = listOf(SupplierEntity(id = "id")),
            downloadState = true,
            deleteState = true,
            activateState = true,
            editState = true,
            activation = true,
        )

        Truth.assertThat(uiState.isLoading).isTrue()
        Truth.assertThat(uiState.isLoadingOverlay).isTrue()
        Truth.assertThat(uiState.isAllSelected).isTrue()

        Truth.assertThat(uiState.itemSelected).isEqualTo(listOf(SupplierEntity(id = "id")))
        Truth.assertThat(uiState.searchQuery).isEqualTo("search")

        Truth.assertThat(uiState.filterData)
            .isEqualTo(SupplierFilterData(activeSelected = listOf(true)))
        Truth.assertThat(uiState.filterOption)
            .isEqualTo(SupplierFilterOption(activeSelected = listOf(OptionData("active", true))))

        Truth.assertThat(uiState.item).isEqualTo(listOf(SupplierEntity(id = "id")))

        Truth.assertThat(uiState.downloadState).isTrue()
        Truth.assertThat(uiState.deleteState).isTrue()
        Truth.assertThat(uiState.activateState).isTrue()
        Truth.assertThat(uiState.editState).isTrue()
        Truth.assertThat(uiState.activation).isTrue()
    }

    @Test
    fun `constructor should return custom value when state's false`() {
        val uiState = SupplierListUiState(
            isLoading = true,
            isLoadingOverlay = true,
            isAllSelected = true,
            itemSelected = listOf(SupplierEntity(id = "id")),
            searchQuery = "search",
            filterData = SupplierFilterData(activeSelected = listOf(true)),
            filterOption = SupplierFilterOption(
                activeSelected = listOf(
                    OptionData(
                        "active",
                        true
                    )
                )
            ),
            item = listOf(SupplierEntity(id = "id")),
            downloadState = false,
            deleteState = false,
            activateState = false,
            editState = false,
            activation = false,

            )

        Truth.assertThat(uiState.isLoading).isTrue()
        Truth.assertThat(uiState.isLoadingOverlay).isTrue()
        Truth.assertThat(uiState.isAllSelected).isTrue()

        Truth.assertThat(uiState.itemSelected).isEqualTo(listOf(SupplierEntity(id = "id")))
        Truth.assertThat(uiState.searchQuery).isEqualTo("search")

        Truth.assertThat(uiState.filterData)
            .isEqualTo(SupplierFilterData(activeSelected = listOf(true)))
        Truth.assertThat(uiState.filterOption)
            .isEqualTo(SupplierFilterOption(activeSelected = listOf(OptionData("active", true))))


        Truth.assertThat(uiState.item).isEqualTo(listOf(SupplierEntity(id = "id")))

        Truth.assertThat(uiState.downloadState).isFalse()
        Truth.assertThat(uiState.deleteState).isFalse()
        Truth.assertThat(uiState.activateState).isFalse()
        Truth.assertThat(uiState.editState).isFalse()
        Truth.assertThat(uiState.activation).isFalse()

    }

    @Test
    fun `queryParams should return null when searchQuery is blank`() {
        // Arrange
        val uiState = SupplierListUiState(searchQuery = "   ")

        // Act
        val queryParams = uiState.queryParams

        // Assert
        Truth.assertThat(queryParams.search).isNull()
    }

    @Test
    fun `queryParams should return correct GetChangeLogQueryParams`() {
        // Arrange
        val uiState = SupplierListUiState(
            searchQuery = "search",
            filterData = SupplierFilterData(
                activeSelected = listOf(true),
                supplierSelected = listOf("supplier"),
                citySelected = listOf("city"),
                itemNameSelected = listOf("item"),
                modifiedBySelected = listOf("User1"),
                date = listOf(123456789L),
            )
        )

        // Act
        val queryParams = uiState.queryParams

        // Assert
        Truth.assertThat(queryParams.search).isEqualTo("search")
        Truth.assertThat(queryParams.active).isEqualTo(Utils.toJsonIfNotEmpty(listOf(true)))
        Truth.assertThat(queryParams.supplier).isEqualTo(Utils.toJsonIfNotEmpty(listOf("supplier")))
        Truth.assertThat(queryParams.city).isEqualTo(Utils.toJsonIfNotEmpty(listOf("city")))
        Truth.assertThat(queryParams.itemName).isEqualTo(Utils.toJsonIfNotEmpty(listOf("item")))
        Truth.assertThat(queryParams.modifiedBy).isEqualTo(Utils.toJsonIfNotEmpty(listOf("User1")))
        Truth.assertThat(queryParams.lastModified)
            .isEqualTo(Utils.toJsonIfNotEmpty(listOf(123456789L)))
    }
}