package com.example.learnsupply.model.supplierlist

import com.google.common.truth.Truth
import com.tagsamurai.tscomponents.button.OptionData
import org.junit.Test

class SupplierFilterOptionTest {
    @Test
    fun `SupplierFilterOption with default values`() {
        // Act
        val data = SupplierFilterOption()

        // Assert
        Truth.assertThat(data.activeSelected).isEmpty()
        Truth.assertThat(data.supplierSelected).isEmpty()
        Truth.assertThat(data.citySelected).isEmpty()
        Truth.assertThat(data.itemNameSelected).isEmpty()
        Truth.assertThat(data.modifiedBySelected).isEmpty()
    }

    @Test
    fun `SupplierFilterOption with custom values`() {
        // Act
        val data = SupplierFilterOption(
            activeSelected = listOf(
                OptionData("active", true),
                OptionData("inactive", false)
            ),
            supplierSelected = listOf(
                OptionData("sup 1", "sup 1"),
                OptionData("sup 2", "sup 2")
            ),
            citySelected = listOf(
                OptionData("city 1", "city 1"),
                OptionData("city 2", "city 2")
            ),
            itemNameSelected = listOf(
                OptionData("item 1", "item 1"),
                OptionData("item 2", "item 2")
            ),
            modifiedBySelected = listOf(
                OptionData("modif 1", "modif 1"),
                OptionData("modif 2", "modif 2")
            ),
        )

        // Assert
        Truth.assertThat(data.activeSelected).isEqualTo(
            listOf(
                OptionData("active", true),
                OptionData("inactive", false)
            )
        )
        Truth.assertThat(data.supplierSelected).isEqualTo(
            listOf(
                OptionData("sup 1", "sup 1"),
                OptionData("sup 2", "sup 2")
            )
        )
        Truth.assertThat(data.citySelected).isEqualTo(
            listOf(
                OptionData("city 1", "city 1"),
                OptionData("city 2", "city 2")
            )
        )
        Truth.assertThat(data.itemNameSelected).isEqualTo(
            listOf(
                OptionData("item 1", "item 1"),
                OptionData("item 2", "item 2")
            )
        )
        Truth.assertThat(data.modifiedBySelected).isEqualTo(
            listOf(
                OptionData("modif 1", "modif 1"),
                OptionData("modif 2", "modif 2")
            )
        )
    }

}