package com.example.learnsupply.model.changelog

import com.google.common.truth.Truth
import com.tagsamurai.tscomponents.button.OptionData
import org.junit.Test

class ChangeLogFilterOptionTest {
    @Test
    fun `ChangeLogFilterOption with default values`() {
        // Act
        val data = ChangeLogFilterOption()

        // Assert
        Truth.assertThat(data.date).isEmpty()
        Truth.assertThat(data.actionSelected).isEmpty()
        Truth.assertThat(data.fieldSelected).isEmpty()
        Truth.assertThat(data.modifiedBySelected).isEmpty()
    }

    @Test
    fun `ChangeLogFilterOption with custom values`() {
        // Act
        val data = ChangeLogFilterOption(
            date = listOf(1234567890L),
            actionSelected = listOf(
                OptionData("act 1", "act 1"),
                OptionData("act 2", "act 2")
            ),
            fieldSelected = listOf(
                OptionData("field 1", "field 1"),
                OptionData("field 2", "field 2")
            ),
            modifiedBySelected = listOf(
                OptionData("modif 1", "modif 1"),
                OptionData("modif 2", "modif 2")
            )
        )

        // Assert
        Truth.assertThat(data.date).isEqualTo(listOf(1234567890L))
        Truth.assertThat(data.actionSelected).isEqualTo(
            listOf(
                OptionData("act 1", "act 1"),
                OptionData("act 2", "act 2")
            )
        )
        Truth.assertThat(data.fieldSelected).isEqualTo(
            listOf(
                OptionData("field 1", "field 1"),
                OptionData("field 2", "field 2")
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