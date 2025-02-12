package com.example.learnsupply.ui.screen.supplier.view.tab.list.model

import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.model.supplierlist.SupplierFilterData
import com.google.common.truth.Truth
import org.junit.Test

class SupplierListCallbackTest {
    @Test
    fun `constructor should return correct object`() {
        // Arrange
        var onRefresh = false
        var onFilter = false
        var onSearch = false
        var onUpdateItemSelected = false
        var onToggleSelectAll = false
        var onDeleteAssetById = false
        var onUpdateActiveById = false
        var onDownload = false
        var onResetSelect = false

        var onResetMessageState = false
        // Act
        val supplierListCb = SupplierListCallback(
            onRefresh = { onRefresh = true },
            onFilter = { onFilter = true },
            onSearch = { onSearch = true },
            onUpdateItemSelected = { onUpdateItemSelected = true },
            onToggleSelectAll = { onToggleSelectAll = true },
            onDeleteAssetById = { onDeleteAssetById = true },
            onUpdateActiveById = { _, _ -> onUpdateActiveById = true },
            onDownload = { onDownload = true },
            onResetSelect = { onResetSelect = true },

            onResetMessageState = { onResetMessageState = true },
        )
        supplierListCb.onRefresh()
        supplierListCb.onFilter(SupplierFilterData(activeSelected = listOf(true)))
        supplierListCb.onSearch("search")
        supplierListCb.onUpdateItemSelected(SupplierEntity(id = "id"))
        supplierListCb.onToggleSelectAll()
        supplierListCb.onDeleteAssetById(listOf("id"))
        supplierListCb.onUpdateActiveById(listOf("id"), true)
        supplierListCb.onDownload("download")
        supplierListCb.onResetSelect()

        supplierListCb.onResetMessageState()

        // Assert
        Truth.assertThat(onRefresh).isTrue()
        Truth.assertThat(onFilter).isTrue()
        Truth.assertThat(onSearch).isTrue()
        Truth.assertThat(onUpdateItemSelected).isTrue()
        Truth.assertThat(onToggleSelectAll).isTrue()
        Truth.assertThat(onDeleteAssetById).isTrue()
        Truth.assertThat(onUpdateActiveById).isTrue()
        Truth.assertThat(onDownload).isTrue()
        Truth.assertThat(onResetSelect).isTrue()

        Truth.assertThat(onResetMessageState).isTrue()
    }
}