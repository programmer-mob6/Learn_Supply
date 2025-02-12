package com.example.learnsupply.ui.screen.detailsupplier.uistate

import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.model.supplierdetail.SupplierDetailFilterData
import com.example.learnsupply.model.supplierdetail.SupplierDetailFilterOption
import com.example.learnsupply.ui.screen.detailsupplier.model.DetailSupplierTab
import com.google.common.truth.Truth
import com.tagsamurai.tscomponents.button.OptionData
import org.junit.Test

class SupplierDetailUiStateTest {
    @Test
    fun `constructor should return default value`() {
        val uiState = SupplierDetailUiState()

        Truth.assertThat(uiState.isLoading).isFalse()
        Truth.assertThat(uiState.isLoadingOverlay).isFalse()
        Truth.assertThat(uiState.searchQuery).isEqualTo("")
        Truth.assertThat(uiState.filterData).isEqualTo(SupplierDetailFilterData())
        Truth.assertThat(uiState.filterOption).isEqualTo((SupplierDetailFilterOption()))
        Truth.assertThat(uiState.deleteState).isNull()
        Truth.assertThat(uiState.supplierDetail).isEqualTo(SupplierEntity())
        Truth.assertThat(uiState.curTabIdx).isEqualTo(0)
    }

    @Test
    fun `constructor should return custom value`() {
        val uiState = SupplierDetailUiState(
            isLoading = true,
            isLoadingOverlay = true,
            searchQuery = "search",
            filterData = SupplierDetailFilterData(picSelected = listOf("pic")),
            filterOption = SupplierDetailFilterOption(
                picSelected = listOf(
                    OptionData(
                        "pic",
                        "pic"
                    )
                )
            ),
            deleteState = true,
            supplierDetail = SupplierEntity(id = "id"),
            curTabIdx = 1
        )

        Truth.assertThat(uiState.isLoading).isTrue()
        Truth.assertThat(uiState.isLoadingOverlay).isTrue()
        Truth.assertThat(uiState.searchQuery).isEqualTo("search")
        Truth.assertThat(uiState.filterData)
            .isEqualTo(SupplierDetailFilterData(picSelected = listOf("pic")))
        Truth.assertThat(uiState.filterOption)
            .isEqualTo((SupplierDetailFilterOption(picSelected = listOf(OptionData("pic", "pic")))))
        Truth.assertThat(uiState.deleteState).isTrue()
        Truth.assertThat(uiState.supplierDetail).isEqualTo(SupplierEntity(id = "id"))
        Truth.assertThat(uiState.curTabIdx).isEqualTo(1)
    }

    @Test
    fun `constructor should return custom value when deleteState false`() {
        val uiState = SupplierDetailUiState(
            isLoading = true,
            isLoadingOverlay = true,
            searchQuery = "search",
            filterData = SupplierDetailFilterData(picSelected = listOf("pic")),
            filterOption = SupplierDetailFilterOption(
                picSelected = listOf(
                    OptionData(
                        "pic",
                        "pic"
                    )
                )
            ),
            deleteState = false,
            supplierDetail = SupplierEntity(id = "id"),
            curTabIdx = 1
        )

        Truth.assertThat(uiState.isLoading).isTrue()
        Truth.assertThat(uiState.isLoadingOverlay).isTrue()
        Truth.assertThat(uiState.searchQuery).isEqualTo("search")
        Truth.assertThat(uiState.filterData)
            .isEqualTo(SupplierDetailFilterData(picSelected = listOf("pic")))
        Truth.assertThat(uiState.filterOption)
            .isEqualTo((SupplierDetailFilterOption(picSelected = listOf(OptionData("pic", "pic")))))
        Truth.assertThat(uiState.deleteState).isFalse()
        Truth.assertThat(uiState.supplierDetail).isEqualTo(SupplierEntity(id = "id"))
        Truth.assertThat(uiState.curTabIdx).isEqualTo(1)
    }

    @Test
    fun `curTab should return correct DetailSupplierTab`() {
        val uiState = SupplierDetailUiState(
            curTabIdx = 0
        )

        val currentTab = uiState.curTab

        Truth.assertThat(currentTab).isEqualTo(DetailSupplierTab.SUPPLIER_ACTIVITIES)
    }
}