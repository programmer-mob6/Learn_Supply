package com.example.learnsupply.ui.screen.addsupplier.model

import com.google.common.truth.Truth
import org.junit.Test

class AddSupplierFormErrorTest {
    @Test
    fun `AddSupplierFormError with default values`() {
        // Act
        val data = AddSupplierFormError()

        // Assert
        Truth.assertThat(data.companyName).isNull()
        Truth.assertThat(data.itemName).isNull()
        Truth.assertThat(data.itemSku).isNull()
        Truth.assertThat(data.country).isNull()
        Truth.assertThat(data.state).isNull()
        Truth.assertThat(data.city).isNull()
        Truth.assertThat(data.zip).isNull()
        Truth.assertThat(data.companyAddress).isNull()
        Truth.assertThat(data.companyNumber).isNull()
        Truth.assertThat(data.picName).isNull()
        Truth.assertThat(data.picNumber).isNull()
        Truth.assertThat(data.picEmail).isNull()
    }

    @Test
    fun `AddSupplierFormError with custom values`() {
        // Act
        val data = AddSupplierFormError(
            companyName = "company",
            itemName = "item",
            itemSku = "sku",
            country = "country",
            state = "state",
            city = "city",
            zip = "12345",
            companyAddress = "address",
            companyNumber = "87654321",
            picName = "pic",
            picNumber = "12345678",
            picEmail = "email"
        )

        // Assert
        Truth.assertThat(data.companyName).isEqualTo("company")
        Truth.assertThat(data.itemName).isEqualTo("item")
        Truth.assertThat(data.itemSku).isEqualTo("sku")
        Truth.assertThat(data.country).isEqualTo("country")
        Truth.assertThat(data.state).isEqualTo("state")
        Truth.assertThat(data.city).isEqualTo("city")
        Truth.assertThat(data.zip).isEqualTo("12345")
        Truth.assertThat(data.companyAddress).isEqualTo("address")
        Truth.assertThat(data.companyNumber).isEqualTo("87654321")
        Truth.assertThat(data.picName).isEqualTo("pic")
        Truth.assertThat(data.picNumber).isEqualTo("12345678")
        Truth.assertThat(data.picEmail).isEqualTo("email")
    }

    @Test
    fun `hasError should return true when at least one field is not null`() {
        // Act
        val formError1 = AddSupplierFormError(companyName = "companyName")
        val formError2 = AddSupplierFormError(itemName = "itemName")
        val formError3 = AddSupplierFormError(itemSku = "itemSku")
        val formError4 = AddSupplierFormError(country = "country")
        val formError5 = AddSupplierFormError(state = "state")
        val formError6 = AddSupplierFormError(city = "city")
        val formError7 = AddSupplierFormError(zip = "zip")
        val formError8 = AddSupplierFormError(companyAddress = "companyAddress")
        val formError9 = AddSupplierFormError(companyNumber = "companyNumber")
        val formError10 = AddSupplierFormError(picName = "picName")
        val formError11 = AddSupplierFormError(picNumber = "picNumber")
        val formError12 = AddSupplierFormError(picEmail = "Invalid Email")

        // Assert
        Truth.assertThat(formError1.hasError()).isTrue()
        Truth.assertThat(formError2.hasError()).isTrue()
        Truth.assertThat(formError3.hasError()).isTrue()
        Truth.assertThat(formError4.hasError()).isTrue()
        Truth.assertThat(formError5.hasError()).isTrue()
        Truth.assertThat(formError6.hasError()).isTrue()
        Truth.assertThat(formError7.hasError()).isTrue()
        Truth.assertThat(formError8.hasError()).isTrue()
        Truth.assertThat(formError9.hasError()).isTrue()
        Truth.assertThat(formError10.hasError()).isTrue()
        Truth.assertThat(formError11.hasError()).isTrue()
        Truth.assertThat(formError12.hasError()).isTrue()
    }

    @Test
    fun `hasError should return true when all fields are not null`() {
        // Act
        val formError = AddSupplierFormError(
            companyName = "companyName",
            itemName = "itemName",
            itemSku = "itemSku",
            country = "country",
            state = "state",
            city = "city",
            zip = "zip",
            companyAddress = "companyAddress",
            companyNumber = "companyNumber",
            picName = "picName",
            picNumber = "picNumber",
            picEmail = "picEmail"
        )

        // Assert
        Truth.assertThat(formError.hasError()).isTrue()
    }
}