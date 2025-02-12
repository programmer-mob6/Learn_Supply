package com.example.learnsupply.ui.screen.detailsupplier.model

import com.example.learnsupply.R
import com.google.common.truth.Truth
import org.junit.Test

class DetailSupplierTabTest {
    @Test
    fun `DetailSupplierTab should have correct number of entries`() {
        // Act
        val entries = DetailSupplierTab.entries

        // Assert
        Truth.assertThat(entries.size).isEqualTo(1)
    }

    @Test
    fun `SUPPLIER_ACTIVITIES should have correct label`() {
        // Act
        val tab = DetailSupplierTab.SUPPLIER_ACTIVITIES

        // Assert
        Truth.assertThat(tab.label).isEqualTo(R.string.title_supplier_activities)
    }
}