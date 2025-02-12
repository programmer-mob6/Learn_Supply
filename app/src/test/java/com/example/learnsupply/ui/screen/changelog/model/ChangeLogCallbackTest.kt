package com.example.learnsupply.ui.screen.changelog.model

import com.example.learnsupply.model.changelog.ChangeLogFilterData
import com.google.common.truth.Truth
import org.junit.Test

class ChangeLogCallbackTest {
    @Test
    fun `constructor should return correct object`() {
        // Arrange
        var onFilter = false
        var onSearch = false
        var onRefresh = false
        var onDownload = false
        var onResetMessageState = false
        // Act
        val changeLogCb = ChangeLogCallback(
            onFilter = { onFilter = true },
            onSearch = { onSearch = true },
            onRefresh = { onRefresh = true },
            onDownload = { onDownload = true },
            onResetMessageState = { onResetMessageState = true },
        )
        changeLogCb.onFilter(ChangeLogFilterData(actionSelected = listOf("action")))
        changeLogCb.onSearch("search")
        changeLogCb.onRefresh()
        changeLogCb.onDownload("download")
        changeLogCb.onResetMessageState()

        // Assert
        Truth.assertThat(onFilter).isTrue()
        Truth.assertThat(onSearch).isTrue()
        Truth.assertThat(onRefresh).isTrue()
        Truth.assertThat(onDownload).isTrue()
        Truth.assertThat(onResetMessageState).isTrue()
    }
}