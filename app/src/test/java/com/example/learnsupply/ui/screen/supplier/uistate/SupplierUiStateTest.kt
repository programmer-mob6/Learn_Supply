package com.example.learnsupply.ui.screen.supplier.uistate

import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.model.SupllierTabEnum
import com.google.common.truth.Truth
import org.junit.Test

class SupplierUiStateTest {
    @Test
    fun `constructor should return default value`() {
        val uiState = SupplierUiState()

        Truth.assertThat(uiState.itemSelected).isEmpty()
        Truth.assertThat(uiState.downloadState).isNull()
        Truth.assertThat(uiState.curTabIdx).isEqualTo(0)
    }

    @Test
    fun `constructor should return custom value`() {
        val uiState = SupplierUiState(
            itemSelected = listOf(SupplierEntity(id = "id")),
            downloadState = true,
            curTabIdx = 1
        )

        Truth.assertThat(uiState.itemSelected).isEqualTo(listOf(SupplierEntity(id = "id")))
        Truth.assertThat(uiState.downloadState).isTrue()
        Truth.assertThat(uiState.curTabIdx).isEqualTo(1)
    }

    @Test
    fun `constructor should return custom value when downloadState false`() {
        val uiState = SupplierUiState(
            itemSelected = listOf(SupplierEntity(id = "id")),
            downloadState = false,
            curTabIdx = 1
        )

        Truth.assertThat(uiState.itemSelected).isEqualTo(listOf(SupplierEntity(id = "id")))
        Truth.assertThat(uiState.downloadState).isFalse()
        Truth.assertThat(uiState.curTabIdx).isEqualTo(1)
    }


    @Test
    fun `curTab should return correct SupllierTabEnum`() {
        val uiState1 = SupplierUiState(
            curTabIdx = 0
        )
        val uiState2 = SupplierUiState(
            curTabIdx = 1
        )

        val currentTab1 = uiState1.curTab
        val currentTab2 = uiState2.curTab

        Truth.assertThat(currentTab1).isEqualTo(SupllierTabEnum.LIST)
        Truth.assertThat(currentTab2).isEqualTo(SupllierTabEnum.ACTIVITIES)
    }
}