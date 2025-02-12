package com.example.learnsupply.ui.screen.supplier.view.tab.list.viewmodel

import com.example.apiservices.base.OptionData
import com.example.apiservices.base.Result
import com.example.apiservices.data.model.supplier.OptionDataBoolean
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.apiservices.data.model.supplier.SupplierFilterEntity
import com.example.apiservices.data.source.network.model.request.DeleteSupplierRequestBody
import com.example.apiservices.data.source.network.model.request.GetSupplierQueryParams
import com.example.apiservices.data.source.network.model.request.PutSupplierIsActiveRequestBody
import com.example.apiservices.domain.DeleteAssetByIdUseCase
import com.example.apiservices.domain.GetSupplierFilterUseCase
import com.example.apiservices.domain.GetSupplierUseCase
import com.example.apiservices.domain.PutIsActiveSupplierUseCase
import com.example.learnsupply.MainDispatcherRule
import com.example.learnsupply.model.supplierlist.SupplierFilterData
import com.example.learnsupply.model.supplierlist.SupplierFilterOption
import com.example.learnsupply.ui.screen.supplier.view.tab.list.model.SupplierListCallback
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

class SupplierListViewModelTest {
    private lateinit var viewModel: SupplierListViewModel
    private lateinit var callback: SupplierListCallback

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var getSupplierUseCase: GetSupplierUseCase

    @MockK
    private lateinit var getSupplierFilterUseCase: GetSupplierFilterUseCase

    @MockK
    private lateinit var deleteAssetByIdUseCase: DeleteAssetByIdUseCase

    @MockK
    private lateinit var putIsActiveSupplierUseCase: PutIsActiveSupplierUseCase

    @MockK
    private lateinit var exportUtil: ExportUtil

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = SupplierListViewModel(
            getSupplierUseCase = getSupplierUseCase,
            getSupplierFilterUseCase = getSupplierFilterUseCase,
            deleteAssetByIdUseCase = deleteAssetByIdUseCase,
            putIsActiveSupplierUseCase = putIsActiveSupplierUseCase,
            exportUtil = exportUtil
        )
        callback = viewModel.getCallback()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun arrangeGetSupplierUseCase(
        query: GetSupplierQueryParams,
        result: Result<List<SupplierEntity>>,
    ) {
        coEvery {
            getSupplierUseCase(
                queryParams = query
            )
        } returns flowOf(result)
    }

    private fun arrangeGetSupplierFilterUseCase(
        result: Result<SupplierFilterEntity> = Result.Success(SupplierFilterEntity()),
    ) {
        coEvery {
            getSupplierFilterUseCase()
        } returns flowOf(result)
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

    private fun arrangePutIsActiveSupplierUseCase(
        body: PutSupplierIsActiveRequestBody,
        result: Result<Unit>,
    ) {
        coEvery {
            putIsActiveSupplierUseCase(
                body = body
            )
        } returns flowOf(result)
    }

    @Test
    fun `When init success should return Result Success data`() =
        runTest {
            arrangeGetSupplierUseCase(
                query = GetSupplierQueryParams(),
                result = Result.Success(
                    listOf(
                        SupplierEntity(id = "id1"),
                        SupplierEntity(id = "id2")
                    )
                )
            )

            arrangeGetSupplierFilterUseCase(
                result = Result.Success(
                    SupplierFilterEntity(
                        active = listOf(
                            OptionDataBoolean(label = true, value = true)
                        ),
                        supplier = listOf(
                            OptionData("suplier1", "suplier1")
                        ),
                        city = listOf(
                            OptionData("city", "city")
                        ),
                        itemName = listOf(
                            OptionData("itemName", "itemName")
                        ),
                        modifiedBy = listOf(
                            OptionData("modifiedBy", "modifiedBy")
                        )
                    )
                )
            )

            viewModel.init()

            val state = viewModel.uiState.value

            coVerify {
                getSupplierUseCase(
                    queryParams = GetSupplierQueryParams()
                )
                getSupplierFilterUseCase()
            }

            Truth.assertThat(state.isLoading).isFalse()
            Truth.assertThat(state.item).isEqualTo(
                listOf(
                    SupplierEntity(id = "id1"),
                    SupplierEntity(id = "id2")
                )
            )
            Truth.assertThat(state.filterOption).isEqualTo(
                SupplierFilterOption(
                    activeSelected = listOf(
                        com.tagsamurai.tscomponents.button.OptionData("Active", true)

                    ),
                    supplierSelected = listOf(
                        com.tagsamurai.tscomponents.button.OptionData("suplier1", "suplier1")
                    ),
                    citySelected = listOf(
                        com.tagsamurai.tscomponents.button.OptionData("city", "city")
                    ),
                    itemNameSelected = listOf(
                        com.tagsamurai.tscomponents.button.OptionData("itemName", "itemName")
                    ),
                    modifiedBySelected = listOf(
                        com.tagsamurai.tscomponents.button.OptionData("modifiedBy", "modifiedBy")
                    )
                )
            )
        }

    @Test
    fun `When init success should return Result Error`() =
        runTest {
            arrangeGetSupplierUseCase(
                query = GetSupplierQueryParams(),
                result = Result.Error("error get supplier")
            )

            arrangeGetSupplierFilterUseCase(
                result = Result.Error("error get filter")
            )

            viewModel.init()

            val state = viewModel.uiState.value

            coVerify {
                getSupplierUseCase(
                    queryParams = GetSupplierQueryParams()
                )
                getSupplierFilterUseCase()
            }

            Truth.assertThat(state.isLoading).isFalse()
            Truth.assertThat(state.item).isEmpty()
            Truth.assertThat(state.filterOption).isEqualTo(SupplierFilterOption())
        }

    @Test
    fun `getCallback onRefresh success should return Result Success data`() =
        runTest {
            arrangeGetSupplierUseCase(
                query = GetSupplierQueryParams(),
                result = Result.Success(
                    listOf(
                        SupplierEntity(id = "id1"),
                        SupplierEntity(id = "id2")
                    )
                )
            )

            arrangeGetSupplierFilterUseCase(
                result = Result.Success(
                    SupplierFilterEntity(
                        supplier = listOf(
                            OptionData("suplier1", "suplier1")
                        )
                    )
                )
            )

            callback.onRefresh()

            val state = viewModel.uiState.value

            coVerify {
                getSupplierUseCase(
                    queryParams = GetSupplierQueryParams()
                )
                getSupplierFilterUseCase()
            }

            Truth.assertThat(state.isLoading).isFalse()
            Truth.assertThat(state.item).isEqualTo(
                listOf(
                    SupplierEntity(id = "id1"),
                    SupplierEntity(id = "id2")
                )
            )
            Truth.assertThat(state.filterOption).isEqualTo(
                SupplierFilterOption(
                    supplierSelected = listOf(
                        com.tagsamurai.tscomponents.button.OptionData("suplier1", "suplier1")
                    )
                )
            )
        }

    @Test
    fun `getCallback onRefresh success should return Result Error`() =
        runTest {
            arrangeGetSupplierUseCase(
                query = GetSupplierQueryParams(),
                result = Result.Error("error get supplier")
            )

            arrangeGetSupplierFilterUseCase(
                result = Result.Error("error get filter")
            )

            callback.onRefresh()

            val state = viewModel.uiState.value

            coVerify {
                getSupplierUseCase(
                    queryParams = GetSupplierQueryParams()
                )
                getSupplierFilterUseCase()
            }

            Truth.assertThat(state.isLoading).isFalse()
            Truth.assertThat(state.item).isEmpty()
            Truth.assertThat(state.filterOption).isEqualTo(SupplierFilterOption())
        }

    @Test
    fun `getCallback onFilter success should return update uistate`() =
        runTest {
            val filterData = SupplierFilterData(
                supplierSelected = listOf("supplier1")
            )

            val queryParams = GetSupplierQueryParams(
                supplier = Utils.toJsonIfNotEmpty(listOf("supplier1"))
            )

            arrangeGetSupplierUseCase(
                query = queryParams,
                result = Result.Success(
                    listOf(SupplierEntity(id = "id1"))
                )
            )

            callback.onFilter(filterData)

            val state = viewModel.uiState.value

            coVerify {
                getSupplierUseCase(
                    queryParams = queryParams
                )
            }

            Truth.assertThat(state.queryParams).isEqualTo(queryParams)
            Truth.assertThat(state.searchQuery).isEmpty()
            Truth.assertThat(state.filterData).isEqualTo(filterData)
            Truth.assertThat(state.isLoading).isFalse()
            Truth.assertThat(state.item).isEqualTo(
                listOf(SupplierEntity(id = "id1"))
            )
        }

    @Test
    fun `getCallback onSearch success should return update uistate`() =
        runTest {
            val queryParams = GetSupplierQueryParams(
                search = "search"
            )

            arrangeGetSupplierUseCase(
                query = queryParams,
                result = Result.Success(
                    listOf(SupplierEntity(id = "id1"))
                )
            )

            callback.onSearch("search")

            val state = viewModel.uiState.value

            coVerify {
                getSupplierUseCase(
                    queryParams = queryParams
                )
            }

            Truth.assertThat(state.filterData).isEqualTo(SupplierFilterData())
            Truth.assertThat(state.queryParams).isEqualTo(queryParams)
            Truth.assertThat(state.searchQuery).isEqualTo("search")
            Truth.assertThat(state.isLoading).isFalse()
            Truth.assertThat(state.item).isEqualTo(
                listOf(SupplierEntity(id = "id1"))
            )
        }

    @Test
    fun `getCallback onToggleSelectAll success should return update uistate`() =
        runTest {
            callback.onToggleSelectAll()

            val state = viewModel.uiState.value

            Truth.assertThat(state.isAllSelected).isTrue()

            callback.onToggleSelectAll()

            val newState = viewModel.uiState.value

            Truth.assertThat(newState.isAllSelected).isFalse()
        }

    @Test
    fun `getCallback onDeleteAssetById success should return Result Success data`() =
        runTest {
            val body = DeleteSupplierRequestBody(listOf("id1"))

            arrangeDeleteAssetByIdUseCase(
                body = body,
                result = Result.Success(Unit)
            )

            arrangeGetSupplierUseCase(
                query = GetSupplierQueryParams(),
                result = Result.Success(
                    listOf(SupplierEntity())
                )
            )

            arrangeGetSupplierFilterUseCase(
                result = Result.Success(
                    SupplierFilterEntity()
                )
            )

            callback.onDeleteAssetById(listOf("id1"))

            coVerify {
                deleteAssetByIdUseCase(body)
                getSupplierUseCase(GetSupplierQueryParams())
                getSupplierFilterUseCase()
            }

            val state = viewModel.uiState.value

            Truth.assertThat(state.isLoadingOverlay).isFalse()
            Truth.assertThat(state.isLoading).isFalse()
            Truth.assertThat(state.itemSelected).isEmpty()
            Truth.assertThat(state.deleteState).isTrue()
        }

    @Test
    fun `getCallback onDeleteAssetById error should return Result error`() =
        runTest {
            val body = DeleteSupplierRequestBody(listOf("id1"))

            arrangeDeleteAssetByIdUseCase(
                body = body,
                result = Result.Error("error")
            )

            callback.onDeleteAssetById(listOf("id1"))

            coVerify {
                deleteAssetByIdUseCase(body)
            }

            val state = viewModel.uiState.value

            Truth.assertThat(state.isLoadingOverlay).isFalse()
            Truth.assertThat(state.isLoading).isFalse()
            Truth.assertThat(state.deleteState).isFalse()
        }

    @Test
    fun `onDownload should update downloadState based on result`() =
        runTest {
            val mockData = listOf(
                SupplierEntity(
                    companyName = "company",
                    suppliedItemSku = listOf("sku1", "sku2"),
                    isActive = "Active",
                    city = "city",
                    country = "country",
                    lastModified = "2026-01-23T17:00:00.000Z",
                    pic = "pic"
                )
            )
            val mockParams = GetSupplierQueryParams()
            val filename = "test.xlsx"
            val formattedDate = "2026-01-23T17:00:00.000Z".toDateFormatter()
            val expectedContent = listOf(
                mapOf(
                    "Company Name" to "company",
                    "Supplied Item Sku" to "[sku1, sku2]",
                    "Active Status" to "Active",
                    "City" to "city",
                    "Country" to "country",
                    "Last Modified" to formattedDate,
                    "PIC" to "pic",
                )
            )
            val exportSlot = slot<List<Map<String, String>>>()

            coEvery {
                exportUtil.exportToExcel(
                    eq(filename),
                    capture(exportSlot)
                )
            } just Runs

            arrangeGetSupplierUseCase(
                query = mockParams,
                result = Result.Success(mockData)
            )

            callback.onDownload(filename)

            val state = viewModel.uiState.value
            Truth.assertThat(state.isLoadingOverlay).isFalse()
            Truth.assertThat(state.downloadState).isTrue()

            coVerify {
                getSupplierUseCase(mockParams)
                exportUtil.exportToExcel(eq(filename), expectedContent)
            }

            Truth.assertThat(exportSlot.captured).isEqualTo(expectedContent)
        }

    @Test
    fun `onDownload should update uistate when result error`() =
        runTest {
            val mockParams = GetSupplierQueryParams()
            val filename = "test.xlsx"

            arrangeGetSupplierUseCase(
                query = mockParams,
                result = Result.Error("error")
            )

            callback.onDownload(filename)

            val state = viewModel.uiState.value
            Truth.assertThat(state.isLoadingOverlay).isFalse()
            Truth.assertThat(state.downloadState).isFalse()

            coVerify {
                getSupplierUseCase(mockParams)
            }
        }

    @Test
    fun `getCallback onUpdateItemSelected success should update uistate`() =
        runTest {
            val mockData = SupplierEntity(id = "id")

            callback.onUpdateItemSelected(mockData)

            val state = viewModel.uiState.value
            Truth.assertThat(state.itemSelected).isEqualTo(listOf(mockData))

            callback.onUpdateItemSelected(mockData)

            val newState = viewModel.uiState.value
            Truth.assertThat(newState.itemSelected).isEmpty()
        }

    @Test
    fun `getCallback onResetSelect success should update uistate`() =
        runTest {
            callback.onResetSelect()

            val newState = viewModel.uiState.value
            Truth.assertThat(newState.itemSelected).isEmpty()
        }

    @Test
    fun `getCallback onUpdateActiveById success should update uistate`() =
        runTest {
            val putBody = PutSupplierIsActiveRequestBody(
                ids = listOf("id1", "id2"),
                isActive = true
            )

            arrangeGetSupplierUseCase(
                query = GetSupplierQueryParams(),
                result = Result.Success(
                    listOf(
                        SupplierEntity(id = "id1"),
                        SupplierEntity(id = "id2")
                    )
                )
            )

            arrangeGetSupplierFilterUseCase(
                result = Result.Success(
                    SupplierFilterEntity(
                        supplier = listOf(
                            OptionData("suplier1", "suplier1")
                        )
                    )
                )
            )

            arrangePutIsActiveSupplierUseCase(
                body = putBody,
                result = Result.Success(Unit)
            )

            callback.onUpdateActiveById(listOf("id1", "id2"), true)

            val newState = viewModel.uiState.value
            Truth.assertThat(newState.isLoadingOverlay).isFalse()
            Truth.assertThat(newState.isLoading).isFalse()
            Truth.assertThat(newState.itemSelected).isEmpty()
            Truth.assertThat(newState.activateState).isTrue()
            Truth.assertThat(newState.activation).isTrue()
        }

    @Test
    fun `getCallback onUpdateActiveById error should update uistate`() =
        runTest {
            val putBody = PutSupplierIsActiveRequestBody(
                ids = listOf("id1", "id2"),
                isActive = true
            )

            arrangePutIsActiveSupplierUseCase(
                body = putBody,
                result = Result.Error("error")
            )

            callback.onUpdateActiveById(listOf("id1", "id2"), true)

            val newState = viewModel.uiState.value
            Truth.assertThat(newState.isLoadingOverlay).isFalse()
            Truth.assertThat(newState.isLoading).isFalse()
            Truth.assertThat(newState.itemSelected).isEmpty()
            Truth.assertThat(newState.activateState).isFalse()
            Truth.assertThat(newState.activation).isNull()
        }

    @Test
    fun `getCallback onResetMessageState success should update uistate`() =
        runTest {
            callback.onResetMessageState()

            val newState = viewModel.uiState.value
            Truth.assertThat(newState.deleteState).isNull()
            Truth.assertThat(newState.activateState).isNull()
            Truth.assertThat(newState.downloadState).isNull()
        }
}