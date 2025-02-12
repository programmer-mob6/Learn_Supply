package com.example.learnsupply.model.changelog

import com.google.common.truth.Truth
import org.junit.Test

class ChangeLogFilterDataTest {
    @Test
    fun `ChangeLogFilterData with default values`() {
        // Act
        val data = ChangeLogFilterData()

        // Assert
        Truth.assertThat(data.date).isEmpty()
        Truth.assertThat(data.actionSelected).isEmpty()
        Truth.assertThat(data.fieldSelected).isEmpty()
        Truth.assertThat(data.modifiedBySelected).isEmpty()
    }

    @Test
    fun `ChangeLogFilterData with custom values`() {
        // Act
        val data = ChangeLogFilterData(
            date = listOf(1234567890L),
            actionSelected = listOf("action 1", "action 2"),
            fieldSelected = listOf("field 1", "field 2"),
            modifiedBySelected = listOf("modified 1", "modified 2")
        )

        // Assert
        Truth.assertThat(data.date).isEqualTo(listOf(1234567890L))
        Truth.assertThat(data.actionSelected).isEqualTo(listOf("action 1", "action 2"))
        Truth.assertThat(data.fieldSelected).isEqualTo(listOf("field 1", "field 2"))
        Truth.assertThat(data.modifiedBySelected).isEqualTo(listOf("modified 1", "modified 2"))
    }
}