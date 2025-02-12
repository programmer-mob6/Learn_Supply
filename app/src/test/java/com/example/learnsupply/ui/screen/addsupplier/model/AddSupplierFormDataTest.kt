package com.example.learnsupply.ui.screen.addsupplier.model

import com.google.common.truth.Truth
import org.junit.Test

class AddSupplierFormDataTest {
    @Test
    fun `AddSupplierFormData with default values`() {
        // Act
        val data = AddSupplierFormData()

        // Assert
        Truth.assertThat(data.companyName).isEmpty()
        Truth.assertThat(data.suppliedItem).isEmpty()
        Truth.assertThat(data.country).isEmpty()
        Truth.assertThat(data.state).isEmpty()
        Truth.assertThat(data.city).isEmpty()
        Truth.assertThat(data.zip).isEqualTo(0)
        Truth.assertThat(data.companyAddress).isEmpty()
        Truth.assertThat(data.companyNumber).isEmpty()
        Truth.assertThat(data.picName).isEmpty()
        Truth.assertThat(data.picNumber).isEmpty()
        Truth.assertThat(data.picEmail).isEmpty()
    }

    @Test
    fun `AddSupplierFormData with custom values`() {
        // Act
        val data = AddSupplierFormData(
            companyName = "company",
            suppliedItem = listOf(
                (AddSupplierSuppliedItem(
                    id = 1,
                    itemName = "item",
                    itemSku = listOf("sku1", "sku2")
                ))
            ),
            country = "country",
            state = "state",
            city = "city",
            zip = 12345,
            companyAddress = "companyAddress",
            companyNumber = "companyNumber",
            picName = "picName",
            picNumber = "picNumber",
            picEmail = "picEmail"
        )

        // Assert
        Truth.assertThat(data.companyName).isEqualTo("company")
        Truth.assertThat(data.suppliedItem).isEqualTo(
            listOf(
                (AddSupplierSuppliedItem(
                    id = 1,
                    itemName = "item",
                    itemSku = listOf("sku1", "sku2")
                ))
            )
        )
        Truth.assertThat(data.country).isEqualTo("country")
        Truth.assertThat(data.state).isEqualTo("state")
        Truth.assertThat(data.city).isEqualTo("city")
        Truth.assertThat(data.zip).isEqualTo(12345)
        Truth.assertThat(data.companyAddress).isEqualTo("companyAddress")
        Truth.assertThat(data.companyNumber).isEqualTo("companyNumber")
        Truth.assertThat(data.picName).isEqualTo("picName")
        Truth.assertThat(data.picNumber).isEqualTo("picNumber")
        Truth.assertThat(data.picEmail).isEqualTo("picEmail")
    }

    @Test
    fun `AddSupplierSuppliedItem with default values`() {
        // Act
        val data = AddSupplierSuppliedItem()

        // Assert
        Truth.assertThat(data.id).isEqualTo(0)
        Truth.assertThat(data.itemName).isEmpty()
        Truth.assertThat(data.itemSku).isEmpty()
    }

    @Test
    fun `AddSupplierSuppliedItem with custom values`() {
        // Act
        val data = AddSupplierSuppliedItem(
            id = 1,
            itemName = "item",
            itemSku = listOf("sku1", "sku2")
        )

        // Assert
        Truth.assertThat(data.id).isEqualTo(1)
        Truth.assertThat(data.itemName).isEqualTo("item")
        Truth.assertThat(data.itemSku).isEqualTo(listOf("sku1", "sku2"))
    }
}