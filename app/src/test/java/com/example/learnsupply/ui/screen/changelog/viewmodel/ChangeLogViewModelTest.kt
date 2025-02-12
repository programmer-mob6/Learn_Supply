package com.example.learnsupply.ui.screen.changelog.viewmodel

import com.example.apiservices.base.OptionData
import com.example.apiservices.base.Result
import com.example.apiservices.data.model.supplier.ChangeLogEntity
import com.example.apiservices.data.model.supplier.ChangeLogFilterEntity
import com.example.apiservices.data.source.network.model.request.GetChangeLogQueryParams
import com.example.apiservices.domain.GetChangeLogFilterUseCase
import com.example.apiservices.domain.GetChangeLogUseCase
import com.example.learnsupply.MainDispatcherRule
import com.example.learnsupply.model.changelog.ChangeLogFilterData
import com.example.learnsupply.model.changelog.ChangeLogFilterOption
import com.example.learnsupply.ui.screen.changelog.model.ChangeLogCallback
import com.google.common.truth.Truth
import com.tagsamurai.tscomponents.utils.ExportUtil
import com.tagsamurai.tscomponents.utils.Utils
import com.tagsamurai.tscomponents.utils.Utils.toDateFormatter
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChangeLogViewModelTest {
    private lateinit var viewModel: ChangeLogViewModel
    private lateinit var callback: ChangeLogCallback

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var exportUtil: ExportUtil

    @MockK
    private lateinit var getChangeLogUseCase: GetChangeLogUseCase

    @MockK
    private lateinit var getChangeLogFilterUseCase: GetChangeLogFilterUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = ChangeLogViewModel(
            getChangeLogUseCase = getChangeLogUseCase,
            getChangeLogFilterUseCase = getChangeLogFilterUseCase,
            exportUtil = exportUtil
        )
        callback = viewModel.getCallback()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun arrangeGetChangeLogUseCase(
        queryParams: GetChangeLogQueryParams,
        result: Result<List<ChangeLogEntity>> = Result.Success(listOf(ChangeLogEntity())),
    ) {
        coEvery {
            getChangeLogUseCase(
                queryParams = queryParams
            )
        } returns flowOf(result)

    }

    private fun arrangeGetChangeLogFilterUseCase(
        result: Result<ChangeLogFilterEntity> = Result.Success(ChangeLogFilterEntity()),
    ) {
        coEvery {
            getChangeLogFilterUseCase()
        } returns flowOf(result)
    }

    @Test
    fun `When init success should return Result Success data`() =
        runTest {
            // Arrange
            val expectedList = listOf(ChangeLogEntity(action = "action"))
            val expectedFilter =
                ChangeLogFilterEntity(
                    action = listOf(OptionData(label = "action", value = "action")),
                    field = listOf(OptionData(label = "field", value = "field")),
                    modifiedBy = listOf(OptionData(label = "modifiedBy", value = "modifiedBy")),
                )
            arrangeGetChangeLogUseCase(
                queryParams = GetChangeLogQueryParams(),
                result = Result.Success(expectedList)
            )
            arrangeGetChangeLogFilterUseCase(
                result = Result.Success(expectedFilter)
            )

            // Act
            viewModel.init()


            // Assert
            coVerify {
                getChangeLogUseCase(GetChangeLogQueryParams())
                getChangeLogFilterUseCase()
            }

            Truth.assertThat(viewModel.uiState.value.filterData).isEqualTo(ChangeLogFilterData())
            Truth.assertThat(viewModel.uiState.value.isLoading).isFalse()
            Truth.assertThat(viewModel.uiState.value.item).isEqualTo(expectedList)

            Truth.assertThat(viewModel.uiState.value.filterOption)
                .isEqualTo(
                    ChangeLogFilterOption(
                        actionSelected = listOf(
                            com.tagsamurai.tscomponents.button.OptionData(
                                label = "action",
                                value = "action"
                            )
                        ),
                        fieldSelected = listOf(
                            com.tagsamurai.tscomponents.button.OptionData(
                                label = "field",
                                value = "field"
                            )
                        ),
                        modifiedBySelected = listOf(
                            com.tagsamurai.tscomponents.button.OptionData(
                                label = "modifiedBy",
                                value = "modifiedBy"
                            )
                        ),
                    )
                )
        }

    @Test
    fun `When init error should return Result error`() =
        runTest {
            // Arrange
            arrangeGetChangeLogUseCase(
                queryParams = GetChangeLogQueryParams(),
                result = Result.Error("error")
            )
            arrangeGetChangeLogFilterUseCase(
                result = Result.Error("Error")
            )

            // Act
            viewModel.init()


            // Assert
            coVerify {
                getChangeLogUseCase(GetChangeLogQueryParams())
                getChangeLogFilterUseCase()
            }

            Truth.assertThat(viewModel.uiState.value.isLoading).isFalse()

        }

    @Test
    fun `getCallback onFilter should update uiState and call getChangeLog`() = runTest {
        // Arrange
        val mockFilterData = ChangeLogFilterData(actionSelected = listOf("action"))
        val expectedList = listOf(ChangeLogEntity(action = "action"))
        val expectedQueryParams =
            GetChangeLogQueryParams(action = Utils.toJsonIfNotEmpty(mockFilterData.actionSelected))

        arrangeGetChangeLogUseCase(
            queryParams = expectedQueryParams,
            result = Result.Success(expectedList)
        )

        // Act
        callback.onFilter(mockFilterData)

        // Assert
        val updatedState = viewModel.uiState.value
        Truth.assertThat(updatedState.searchQuery).isEmpty()
        Truth.assertThat(updatedState.filterData).isEqualTo(mockFilterData)
        coVerify { getChangeLogUseCase(expectedQueryParams) }
    }

    @Test
    fun `getCallback onSearch should update uiState and call getChangeLog`() = runTest {
        // Arrange
        val mockQuery = "search"
        val expectedList = listOf(ChangeLogEntity(action = "action"))

        arrangeGetChangeLogUseCase(
            queryParams = GetChangeLogQueryParams(search = "search"),
            result = Result.Success(expectedList)
        )

        // Act
        callback.onSearch(mockQuery)

        // Assert
        val updatedState = viewModel.uiState.value
        Truth.assertThat(updatedState.searchQuery).isEqualTo(mockQuery)
        coVerify { getChangeLogUseCase(GetChangeLogQueryParams(search = "search")) }
    }

    @Test
    fun `onDownload should update downloadState based on result`() = runTest {
        val mockData = listOf(
            ChangeLogEntity(
                action = "Edit",
                companyName = "Company A",
                field = "Status",
                newValue = "Approved",
                oldValue = "Pending",
                lastModified = "2026-01-23T17:00:00.000Z",
                modifiedBy = "Admin"
            )
        )
        val mockParams = GetChangeLogQueryParams()
        val filename = "test.xlsx"
        val formattedDate = "2026-01-23T17:00:00.000Z".toDateFormatter()
        val expectedContent = listOf(
            mapOf(
                "Action" to "Edit",
                "Company Name" to "Company A",
                "Field" to "Status",
                "New Value" to "Approved",
                "Old Value" to "Pending",
                "Last Modified" to formattedDate,
                "Modified By" to "Admin"
            )
        )
        val exportSlot = slot<List<Map<String, String>>>()

        coEvery {
            exportUtil.exportToExcel(
                eq(filename),
                capture(exportSlot)
            )
        } just Runs

        arrangeGetChangeLogUseCase(
            queryParams = mockParams,
            result = Result.Success(mockData)
        )

        callback.onDownload(filename)

        val state = viewModel.uiState.value
        Truth.assertThat(state.isLoadingOverlay).isFalse()
        Truth.assertThat(state.downloadState).isTrue()

        coVerify {
            getChangeLogUseCase(mockParams)
            exportUtil.exportToExcel(eq(filename), expectedContent)
        }

        Truth.assertThat(exportSlot.captured).isEqualTo(expectedContent)
    }

    @Test
    fun `onDownload should update uistate when result error`() =
        runTest {
            val mockParams = GetChangeLogQueryParams()
            val filename = "test.xlsx"

            arrangeGetChangeLogUseCase(
                queryParams = mockParams,
                result = Result.Error("error")
            )

            callback.onDownload(filename)

            val state = viewModel.uiState.value
            Truth.assertThat(state.isLoadingOverlay).isFalse()
            Truth.assertThat(state.downloadState).isFalse()

            coVerify {
                getChangeLogUseCase(mockParams)
            }
        }

    @Test
    fun `resetMessageState should update downloadstate`() = runTest {
        // Act
        callback.onResetMessageState()

        // Assert
        val state = viewModel.uiState.value
        Truth.assertThat(state.downloadState).isNull()
    }
}