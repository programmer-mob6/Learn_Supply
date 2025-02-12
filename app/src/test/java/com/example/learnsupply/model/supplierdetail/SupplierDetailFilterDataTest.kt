package com.example.learnsupply.model.supplierdetail

import com.google.common.truth.Truth
import com.tagsamurai.tscomponents.model.TreeNode
import org.junit.Test

class SupplierDetailFilterDataTest {
    @Test
    fun `SupplierDetailFilterData with default values`() {
        // Act
        val data = SupplierDetailFilterData()

        // Assert
        Truth.assertThat(data.transactionSelected).isEmpty()
        Truth.assertThat(data.group).isEmpty()
        Truth.assertThat(data.picSelected).isEmpty()
        Truth.assertThat(data.date).isEmpty()
    }

    @Test
    fun `SupplierDetailFilterData with custom values`() {
        // Act
        val data = SupplierDetailFilterData(
            transactionSelected = listOf("trans 1", "trans 2"),
            group = listOf(
                TreeNode("A", "A"),
                TreeNode("B", "B")
            ),
            picSelected = listOf("pic 1", "pic 2"),
            date = listOf(1234567890L, 987654321L)
        )

        // Assert
        Truth.assertThat(data.transactionSelected).isEqualTo(listOf("trans 1", "trans 2"))
        Truth.assertThat(data.group).isEqualTo(
            listOf(
                TreeNode("A", "A"),
                TreeNode("B", "B")
            )
        )
        Truth.assertThat(data.picSelected).isEqualTo(listOf("pic 1", "pic 2"))
        Truth.assertThat(data.date).isEqualTo(listOf(1234567890L, 987654321L))
    }
}