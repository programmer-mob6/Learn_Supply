package com.example.learnsupply.ui.screen.detailsupplier.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.apiservices.base.Result
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.apiservices.data.source.network.model.request.DeleteSupplierRequestBody
import com.example.apiservices.domain.DeleteAssetByIdUseCase
import com.example.apiservices.domain.GetSupplierByIdUseCase
import com.example.learnsupply.MainDispatcherRule
import com.example.learnsupply.navigation.ITEM_ID
import com.example.learnsupply.ui.screen.detailsupplier.model.SupplierDetailCallback
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SupplierDetailViewModelTest {
    private lateinit var viewModel: SupplierDetailViewModel
    private lateinit var callback: SupplierDetailCallback
    private val id = "id"

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    private lateinit var getSupplierByIdUseCase: GetSupplierByIdUseCase

    @MockK
    private lateinit var deleteAssetByIdUseCase: DeleteAssetByIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every {
            savedStateHandle.get<String?>(ITEM_ID)
        } returns id

        viewModel = SupplierDetailViewModel(
            savedStateHandle = savedStateHandle,
            getSupplierByIdUseCase = getSupplierByIdUseCase,
            deleteAssetByIdUseCase = deleteAssetByIdUseCase
        )
        callback = viewModel.getCallback()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun arrangeDeleteAssetByIdUseCase(
        body: DeleteSupplierRequestBody,
        result: Result<Unit> = Result.Success(Unit),
    ) {
        coEvery {
            deleteAssetByIdUseCase(
                body = body
            )
        } returns flowOf(result)
    }

    private fun arrangeGetSupplierByIdUseCase(
        result: Result<SupplierEntity> = Result.Success(SupplierEntity()),
    ) {
        coEvery {
            getSupplierByIdUseCase(
                id = id
            )
        } returns flowOf(result)
    }

    @Test
    fun `When getDetailSupplier success should return Result Success data`() =
        runTest {
            arrangeGetSupplierByIdUseCase(
                result = Result.Success(SupplierEntity(id = id))
            )

            viewModel.getDetailSupplier()

            val state = viewModel.uiState.value

            coVerify {
                getSupplierByIdUseCase(id = id)
            }
            Truth.assertThat(state.supplierDetail).isEqualTo(SupplierEntity(id = id))
            Truth.assertThat(state.isLoading).isFalse()
        }

    @Test
    fun `When getDetailSupplier error should return empty data`() =
        runTest {
            arrangeGetSupplierByIdUseCase(
                result = Result.Error("Error")
            )

            viewModel.getDetailSupplier()

            val state = viewModel.uiState.value

            coVerify {
                getSupplierByIdUseCase(id = id)
            }
            Truth.assertThat(state.supplierDetail).isEqualTo(SupplierEntity())
            Truth.assertThat(state.isLoading).isFalse()
        }

    @Test
    fun `onDeleteSupplierById success should update deleteState`() =
        runTest {
            arrangeDeleteAssetByIdUseCase(
                body = DeleteSupplierRequestBody(ids = listOf(id)),
                result = Result.Success(Unit)
            )

            callback.onDeleteSupplierById()

            val state = viewModel.uiState.value
            coVerify {
                deleteAssetByIdUseCase(body = DeleteSupplierRequestBody(ids = listOf(id)))
            }
            Truth.assertThat(state.deleteState).isTrue()
            Truth.assertThat(state.isLoadingOverlay).isFalse()
        }

    @Test
    fun `onDeleteSupplierById error should update deleteState`() =
        runTest {
            arrangeDeleteAssetByIdUseCase(
                body = DeleteSupplierRequestBody(listOf(id)),
                result = Result.Error("Error")
            )

            callback.onDeleteSupplierById()

            val state = viewModel.uiState.value
            coVerify {
                deleteAssetByIdUseCase(body = DeleteSupplierRequestBody(listOf(id)))
            }
            Truth.assertThat(state.deleteState).isFalse()
            Truth.assertThat(state.isLoadingOverlay).isFalse()
        }

    @Test
    fun `changeCurTab should update curTabIdx`() =
        runTest {
            viewModel.changeCurTab(tab = 1)

            val state = viewModel.uiState.value
            Truth.assertThat(state.curTabIdx).isEqualTo(1)
        }

    @Test
    fun `resetMessageState should update downloadstate`() = runTest {
        // Act
        callback.onResetMessageState()

        // Assert
        val state = viewModel.uiState.value
        Truth.assertThat(state.deleteState).isNull()
    }

    @Test
    fun `getCallback should return valid callbacks`() {
        // Arrange
        val viewModel = spyk(
            SupplierDetailViewModel(
                savedStateHandle = savedStateHandle,
                getSupplierByIdUseCase = getSupplierByIdUseCase,
                deleteAssetByIdUseCase = deleteAssetByIdUseCase
            )
        )

        arrangeDeleteAssetByIdUseCase(
            body = DeleteSupplierRequestBody(ids = listOf(id)),
            result = Result.Success(Unit)
        )

        // Act
        val callback = viewModel.getCallback()

        // Assert
        Truth.assertThat(callback).isNotNull()

        callback.onDeleteSupplierById()
        callback.onResetMessageState()

        coVerify { viewModel.deleteSupplier() }
        coVerify { viewModel.resetMessageState() }
    }

}