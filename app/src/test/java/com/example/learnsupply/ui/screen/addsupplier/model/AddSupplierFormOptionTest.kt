package com.example.learnsupply.ui.screen.addsupplier.model

import com.google.common.truth.Truth
import com.tagsamurai.tscomponents.button.OptionData
import org.junit.Test

class AddSupplierFormOptionTest {
    @Test
    fun `AddSupplierFormOption with default values`() {
        // Act
        val data = AddSupplierFormOption()

        // Assert
        Truth.assertThat(data.itemName).isEmpty()
        Truth.assertThat(data.itemSku).isEmpty()
        Truth.assertThat(data.country).isEmpty()
        Truth.assertThat(data.state).isEmpty()
        Truth.assertThat(data.city).isEmpty()
    }

    @Test
    fun `AddSupplierFormOption with custom values`() {
        // Act
        val data = AddSupplierFormOption(
            itemName = listOf(
                OptionData("item 1", "item 1"),
                OptionData("item 2", "item 2")
            ),
            itemSku = listOf(
                OptionData("sku 1", "sku 1"),
                OptionData("sku 2", "sku 2")
            ),
            country = listOf(
                OptionData("country 1", "country 1"),
                OptionData("country 2", "country 2")
            ),
            state = listOf(
                OptionData("state 1", "state 1"),
                OptionData("state 2", "state 2")
            ),
            city = listOf(
                OptionData("city 1", "city 1"),
                OptionData("city 2", "city 2")
            )
        )

        // Assert
        Truth.assertThat(data.itemName).isEqualTo(
            listOf(
                OptionData("item 1", "item 1"),
                OptionData("item 2", "item 2")
            )
        )
        Truth.assertThat(data.itemSku).isEqualTo(
            listOf(
                OptionData("sku 1", "sku 1"),
                OptionData("sku 2", "sku 2")
            )
        )
        Truth.assertThat(data.country).isEqualTo(
            listOf(
                OptionData("country 1", "country 1"),
                OptionData("country 2", "country 2")
            )
        )
        Truth.assertThat(data.state).isEqualTo(
            listOf(
                OptionData("state 1", "state 1"),
                OptionData("state 2", "state 2")
            )
        )
        Truth.assertThat(data.city).isEqualTo(
            listOf(
                OptionData("city 1", "city 1"),
                OptionData("city 2", "city 2")
            )
        )
    }
}