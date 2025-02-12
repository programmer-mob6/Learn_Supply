package com.example.learnsupply.ui.screen.supplier.viewmodel

import com.example.learnsupply.MainDispatcherRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SupplierViewModelTest {

    private lateinit var viewModel: SupplierViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SupplierViewModel()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `changeCurTab should update curTabIdx`() =
        runTest {
            viewModel.changeCurTab(tab = 1)

            val state = viewModel.uiState.value
            Truth.assertThat(state.curTabIdx).isEqualTo(1)
        }
}