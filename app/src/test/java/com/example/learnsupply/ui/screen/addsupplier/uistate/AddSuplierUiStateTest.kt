package com.example.learnsupply.ui.screen.addsupplier.uistate

import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierFormData
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierFormError
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierFormOption
import com.google.common.truth.Truth
import com.tagsamurai.tscomponents.button.OptionData
import org.junit.Test

class AddSuplierUiStateTest {
    @Test
    fun `constructor should return default value`() {
        val uiState = AddSuplierUiState()

        Truth.assertThat(uiState.isLoadingOverlay).isFalse()
        Truth.assertThat(uiState.isLoadingFormOption).isFalse()
        Truth.assertThat(uiState.isEditForm).isFalse()
        Truth.assertThat(uiState.isStayOnForm).isFalse()
        Truth.assertThat(uiState.assetId).isEqualTo("")
        Truth.assertThat(uiState.formData).isEqualTo(AddSupplierFormData())
        Truth.assertThat(uiState.formError).isEqualTo(AddSupplierFormError())
        Truth.assertThat(uiState.formOption).isEqualTo(AddSupplierFormOption())
        Truth.assertThat(uiState.submitState).isNull()
        Truth.assertThat(uiState.suppliedItemIndex).isEqualTo(0)
    }

    @Test
    fun `constructor should return custom value`() {
        val uiState = AddSuplierUiState(
            isLoadingOverlay = true,
            isLoadingFormOption = true,
            isEditForm = true,
            isStayOnForm = true,
            assetId = "id",
            formData = AddSupplierFormData(companyName = "company"),
            formError = AddSupplierFormError(companyName = "company"),
            formOption = AddSupplierFormOption(itemName = listOf(OptionData("item", "item"))),
            submitState = true,
            suppliedItemIndex = 1
        )

        Truth.assertThat(uiState.isLoadingOverlay).isTrue()
        Truth.assertThat(uiState.isLoadingFormOption).isTrue()
        Truth.assertThat(uiState.isEditForm).isTrue()
        Truth.assertThat(uiState.isStayOnForm).isTrue()
        Truth.assertThat(uiState.assetId).isEqualTo("id")
        Truth.assertThat(uiState.formData).isEqualTo(AddSupplierFormData(companyName = "company"))
        Truth.assertThat(uiState.formError).isEqualTo(AddSupplierFormError(companyName = "company"))
        Truth.assertThat(uiState.formOption)
            .isEqualTo(AddSupplierFormOption(itemName = listOf(OptionData("item", "item"))))
        Truth.assertThat(uiState.submitState).isTrue()
        Truth.assertThat(uiState.suppliedItemIndex).isEqualTo(1)
    }

    @Test
    fun `constructor should return custom value when submit state false`() {
        val uiState = AddSuplierUiState(
            isLoadingOverlay = true,
            isLoadingFormOption = true,
            isEditForm = true,
            isStayOnForm = true,
            assetId = "id",
            formData = AddSupplierFormData(companyName = "company"),
            formError = AddSupplierFormError(companyName = "company"),
            formOption = AddSupplierFormOption(itemName = listOf(OptionData("item", "item"))),
            submitState = false,
            suppliedItemIndex = 1
        )

        Truth.assertThat(uiState.isLoadingOverlay).isTrue()
        Truth.assertThat(uiState.isLoadingFormOption).isTrue()
        Truth.assertThat(uiState.isEditForm).isTrue()
        Truth.assertThat(uiState.isStayOnForm).isTrue()
        Truth.assertThat(uiState.assetId).isEqualTo("id")
        Truth.assertThat(uiState.formData).isEqualTo(AddSupplierFormData(companyName = "company"))
        Truth.assertThat(uiState.formError).isEqualTo(AddSupplierFormError(companyName = "company"))
        Truth.assertThat(uiState.formOption)
            .isEqualTo(AddSupplierFormOption(itemName = listOf(OptionData("item", "item"))))
        Truth.assertThat(uiState.submitState).isFalse()
        Truth.assertThat(uiState.suppliedItemIndex).isEqualTo(1)
    }
}