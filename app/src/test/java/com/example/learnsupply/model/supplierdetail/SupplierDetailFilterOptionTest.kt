package com.example.learnsupply.model.supplierdetail

import com.google.common.truth.Truth
import com.tagsamurai.tscomponents.button.OptionData
import com.tagsamurai.tscomponents.model.TreeNode
import org.junit.Test

class SupplierDetailFilterOptionTest {
    @Test
    fun `SupplierDetailFilterOption with default values`() {
        // Act
        val data = SupplierDetailFilterOption()

        // Assert
        Truth.assertThat(data.transactionSelected).isEmpty()
        Truth.assertThat(data.groupOption).isEmpty()
        Truth.assertThat(data.picSelected).isEmpty()
    }

    @Test
    fun `SupplierDetailFilterOption with custom values`() {
        // Act
        val data = SupplierDetailFilterOption(
            transactionSelected = listOf(
                OptionData("trans 1", "trans 1"),
                OptionData("trans 2", "trans 2")
            ),
            groupOption = listOf(
                TreeNode("A", "A"),
                TreeNode("B", "B")
            ),
            picSelected = listOf(
                OptionData("pic 1", "pic 1"),
                OptionData("pic 2", "pic 2")
            )
        )

        // Assert
        Truth.assertThat(data.transactionSelected).isEqualTo(
            listOf(
                OptionData("trans 1", "trans 1"),
                OptionData("trans 2", "trans 2")
            )
        )
        Truth.assertThat(data.groupOption).isEqualTo(
            listOf(
                TreeNode("A", "A"),
                TreeNode("B", "B")
            )
        )
        Truth.assertThat(data.picSelected).isEqualTo(
            listOf(
                OptionData("pic 1", "pic 1"),
                OptionData("pic 2", "pic 2")
            )
        )
    }
}