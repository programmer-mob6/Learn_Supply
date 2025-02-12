package com.example.learnsupply.ui.screen.addsupplier.model

import com.google.common.truth.Truth
import org.junit.Test

class AddSupplierCallbackTest {
    @Test
    fun `constructor should return correct object`() {
        // Arrange
        var onClearField = false
        var onResetMessageState = false
        var onUpdateFormData = false
        var onSubmitForm = false
        var onUpdateStayOnForm = false
        var onAddingSuppliedItem = false
        var removingSuppliedItemById = false
        var onUpdateListSuppliedItem = false

        // Act
        val supplierCb = AddSupplierCallback(
            onClearField = { onClearField = true },
            onResetMessageState = { onResetMessageState = true },
            onUpdateFormData = { onUpdateFormData = true },
            onSubmitForm = { onSubmitForm = true },
            onUpdateStayOnForm = { onUpdateStayOnForm = true },
            onAddingSuppliedItem = { onAddingSuppliedItem = true },
            removingSuppliedItemById = { removingSuppliedItemById = true },
            onUpdateListSuppliedItem = { onUpdateListSuppliedItem = true },
        )
        supplierCb.onClearField()
        supplierCb.onResetMessageState()
        supplierCb.onUpdateFormData(AddSupplierFormData(companyName = "company"))
        supplierCb.onSubmitForm()
        supplierCb.onUpdateStayOnForm()
        supplierCb.onAddingSuppliedItem()
        supplierCb.removingSuppliedItemById(1)
        supplierCb.onUpdateListSuppliedItem(AddSupplierSuppliedItem(id = 1))

        // Assert
        Truth.assertThat(onClearField).isTrue()
        Truth.assertThat(onResetMessageState).isTrue()
        Truth.assertThat(onUpdateFormData).isTrue()
        Truth.assertThat(onSubmitForm).isTrue()
        Truth.assertThat(onUpdateStayOnForm).isTrue()
        Truth.assertThat(onAddingSuppliedItem).isTrue()
        Truth.assertThat(removingSuppliedItemById).isTrue()
        Truth.assertThat(onUpdateListSuppliedItem).isTrue()
    }
}