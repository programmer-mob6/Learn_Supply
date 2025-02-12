package com.example.learnsupply.model.supplierlist

import com.google.common.truth.Truth
import org.junit.Test

class SupplierFilterDataTest {
    @Test
    fun `SupplierFilterData with default values`() {
        // Act
        val data = SupplierFilterData()

        // Assert
        Truth.assertThat(data.activeSelected).isEmpty()
        Truth.assertThat(data.supplierSelected).isEmpty()
        Truth.assertThat(data.citySelected).isEmpty()
        Truth.assertThat(data.itemNameSelected).isEmpty()
        Truth.assertThat(data.modifiedBySelected).isEmpty()
        Truth.assertThat(data.date).isEmpty()
    }

    @Test
    fun `SupplierFilterData with custom values`() {
        // Act
        val data = SupplierFilterData(
            activeSelected = listOf(true, false),
            supplierSelected = listOf("sup 1", "sup 2"),
            citySelected = listOf("city 1", "city 2"),
            itemNameSelected = listOf("item 1", "item 2"),
            modifiedBySelected = listOf("modif 1", "modif 2"),
            date = listOf(123456789L, 987654321L)
        )

        // Assert
        Truth.assertThat(data.activeSelected).isEqualTo(listOf(true, false))
        Truth.assertThat(data.supplierSelected).isEqualTo(listOf("sup 1", "sup 2"))
        Truth.assertThat(data.citySelected).isEqualTo(listOf("city 1", "city 2"))
        Truth.assertThat(data.itemNameSelected).isEqualTo(listOf("item 1", "item 2"))
        Truth.assertThat(data.modifiedBySelected).isEqualTo(listOf("modif 1", "modif 2"))
        Truth.assertThat(data.date).isEqualTo(listOf(123456789L, 987654321L))
    }
}