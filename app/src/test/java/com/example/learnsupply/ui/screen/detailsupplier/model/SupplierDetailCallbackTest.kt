package com.example.learnsupply.ui.screen.detailsupplier.model

import com.example.learnsupply.model.supplierdetail.SupplierDetailFilterData
import com.google.common.truth.Truth
import org.junit.Test

class SupplierDetailCallbackTest {
    @Test
    fun `constructor should return correct object`() {
        // Arrange
        var onFilter = false
        var onSearch = false
        var onDeleteSupplierById = false
        var onResetMessageState = false
        // Act
        val supplierDetailCb = SupplierDetailCallback(
            onFilter = { onFilter = true },
            onSearch = { onSearch = true },
            onDeleteSupplierById = { onDeleteSupplierById = true },
            onResetMessageState = { onResetMessageState = true },
        )
        supplierDetailCb.onFilter(SupplierDetailFilterData(picSelected = listOf("pic")))
        supplierDetailCb.onSearch("search")
        supplierDetailCb.onDeleteSupplierById()
        supplierDetailCb.onResetMessageState()

        // Assert
        Truth.assertThat(onFilter).isTrue()
        Truth.assertThat(onSearch).isTrue()
        Truth.assertThat(onDeleteSupplierById).isTrue()
        Truth.assertThat(onResetMessageState).isTrue()
    }
}