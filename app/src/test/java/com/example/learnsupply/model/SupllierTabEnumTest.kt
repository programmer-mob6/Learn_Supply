package com.example.learnsupply.model

import com.google.common.truth.Truth
import org.junit.Test

class SupllierTabEnumTest {
    @Test
    fun `SupllierTabEnum values are correct`() {
        // Act & Assert
        Truth.assertThat(SupllierTabEnum.LIST.title).isEqualTo("List")
        Truth.assertThat(SupllierTabEnum.ACTIVITIES.title).isEqualTo("Supplier Activities")
    }

    @Test
    fun `SupllierTabEnum has correct number of values`() {
        // Act
        val values = SupllierTabEnum.values()

        // Assert
        Truth.assertThat(values.size).isEqualTo(2)
    }

    @Test
    fun `SupllierTabEnum valueOf works correctly`() {
        // Act & Assert
        Truth.assertThat(SupllierTabEnum.valueOf("LIST")).isEqualTo(SupllierTabEnum.LIST)
        Truth.assertThat(SupllierTabEnum.valueOf("ACTIVITIES"))
            .isEqualTo(SupllierTabEnum.ACTIVITIES)
    }
}