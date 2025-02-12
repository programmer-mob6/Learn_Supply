package com.example.learnsupply.ui.screen.addsupplier.viewmodel

import android.content.Context
import com.example.apiservices.base.Result
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.apiservices.data.source.network.model.request.PostSupplierRequestBody
import com.example.apiservices.data.source.network.model.request.PostSupplierRequestBody.Supplieditem
import com.example.apiservices.data.source.network.model.request.PutSupplierRequestBody
import com.example.apiservices.domain.AddSupplierUseCase
import com.example.apiservices.domain.GetSupplierByIdUseCase
import com.example.apiservices.domain.UpdateSupplierByIdUseCase
import com.example.learnsupply.MainDispatcherRule
import com.example.learnsupply.R
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierCallback
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierFormData
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierFormError
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierSuppliedItem
import com.example.learnsupply.ui.screen.addsupplier.uistate.AddSuplierUiState
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddSupplierViewModelTest {
    private lateinit var viewModel: AddSupplierViewModel
    private lateinit var callback: AddSupplierCallback

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var getSupplierByIdUseCase: GetSupplierByIdUseCase

    @MockK
    private lateinit var addSupplierUseCase: AddSupplierUseCase

    @MockK
    private lateinit var updateSupplierByIdUseCase: UpdateSupplierByIdUseCase

    private val mockContext = mockk<Context>(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { mockContext.getString(R.string.message_error_empty_company_name) } returns "Company Name must not be empty"
        every { mockContext.getString(R.string.message_error_empty_item_name) } returns "You must pick an item name"
        every { mockContext.getString(R.string.message_error_empty_item_sku) } returns "You must pick a SKU"
        every { mockContext.getString(R.string.message_error_empty_country) } returns "You must pick a country"
        every { mockContext.getString(R.string.message_error_empty_state) } returns "You must pick a state"
        every { mockContext.getString(R.string.message_error_empty_city) } returns "You must pick a city"
        every { mockContext.getString(R.string.message_error_empty_zip) } returns "ZIP Code must not be empty"
        every { mockContext.getString(R.string.message_error_empty_company_address) } returns "Company Address must not be empty"
        every { mockContext.getString(R.string.message_error_empty_company_number) } returns "Company Number must not be empty"
        every { mockContext.getString(R.string.message_error_empty_pic_name) } returns "PIC Name must not be empty"
        every { mockContext.getString(R.string.message_error_empty_pic_number) } returns "PIC Number must not be empty"
        every { mockContext.getString(R.string.message_error_empty_pic_email) } returns "PIC Email must not be empty"

        viewModel = AddSupplierViewModel(
            getSupplierByIdUseCase = getSupplierByIdUseCase,
            addSupplierUseCase = addSupplierUseCase,
            updateSupplierByIdUseCase = updateSupplierByIdUseCase,
            context = mockContext
        )
        callback = viewModel.getCallback()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun arrangeGetSupplierByIdUseCase(
        id: String,
        result: Result<SupplierEntity> = Result.Success(SupplierEntity()),
    ) {
        coEvery {
            getSupplierByIdUseCase(
                id = id
            )
        } returns flowOf(result)
    }

    private fun arrangeAddSupplierUseCase(
        body: PostSupplierRequestBody,
        result: Result<Unit> = Result.Success(Unit),
    ) {
        coEvery {
            addSupplierUseCase(
                body = body
            )
        } returns flowOf(result)
    }

    private fun arrangeUpdateSupplierByIdUseCase(
        body: PutSupplierRequestBody,
        result: Result<Unit> = Result.Success(Unit),
    ) {
        coEvery {
            updateSupplierByIdUseCase(
                body = body
            )
        } returns flowOf(result)
    }

    @Test
    fun `When init with id success should return update ui state`() =
        runTest {
            arrangeGetSupplierByIdUseCase(
                id = "id",
                result = Result.Success(SupplierEntity(id = "id", zipCode = "12345"))
            )

            viewModel.init("id")



            coVerify {
                getSupplierByIdUseCase(id = "id")
            }

            val state = viewModel.uiState.value

            Truth.assertThat(state.submitState).isNull()
            Truth.assertThat(state.formError).isEqualTo(AddSupplierFormError())

            Truth.assertThat(state.formData).isEqualTo(AddSupplierFormData(zip = 12345))

            Truth.assertThat(state.isLoadingOverlay).isFalse()
            Truth.assertThat(state.assetId).isEqualTo("id")
            Truth.assertThat(state.isEditForm).isTrue()
        }

    @Test
    fun `When init without id success should return update ui state`() =
        runTest {
            viewModel.init()

            val state = viewModel.uiState.value

            Truth.assertThat(state.submitState).isNull()
            Truth.assertThat(state.formError).isEqualTo(AddSupplierFormError())

            Truth.assertThat(state.formData).isEqualTo(
                AddSupplierFormData(
                    suppliedItem = listOf(
                        AddSupplierSuppliedItem(id = state.suppliedItemIndex)
                    )
                )
            )

            Truth.assertThat(state.assetId).isEqualTo("")
            Truth.assertThat(state.isEditForm).isFalse()
        }

    @Test
    fun `When init with id error should return update ui state`() =
        runTest {
            arrangeGetSupplierByIdUseCase(
                id = "id",
                result = Result.Error("error")
            )

            viewModel.init("id")

            coVerify {
                getSupplierByIdUseCase(id = "id")
            }

            val state = viewModel.uiState.value

            Truth.assertThat(state.submitState).isNull()
            Truth.assertThat(state.formError).isEqualTo(AddSupplierFormError())

            Truth.assertThat(state.formData)
                .isEqualTo(AddSupplierFormData(suppliedItem = listOf(AddSupplierSuppliedItem(id = state.suppliedItemIndex))))

            Truth.assertThat(state.isLoadingOverlay).isFalse()
            Truth.assertThat(state.assetId).isEqualTo("")
            Truth.assertThat(state.isEditForm).isFalse()
        }

    @Test
    fun `updateFormData success should update uistate`() =
        runTest {
            callback.onUpdateFormData(AddSupplierFormData(companyName = "company"))

            val state = viewModel.uiState.value

            Truth.assertThat(state.formData).isEqualTo(AddSupplierFormData(companyName = "company"))
            Truth.assertThat(state.formError).isEqualTo(AddSupplierFormError())
        }

    @Test
    fun `updateOnStayForm success should update uistate`() =
        runTest {
            callback.onUpdateStayOnForm()

            val state = viewModel.uiState.value

            Truth.assertThat(state.isStayOnForm).isTrue()
        }


    @Test
    fun `addingSuppliedItem success should update uistate`() =
        runTest {
            callback.onAddingSuppliedItem()

            val state = viewModel.uiState.value

            Truth.assertThat(state.suppliedItemIndex).isEqualTo(1)
            Truth.assertThat(state.formData)
                .isEqualTo(AddSupplierFormData(suppliedItem = listOf(AddSupplierSuppliedItem(id = state.suppliedItemIndex))))
        }

    @Test
    fun `removingSuppliedItemById success should update uistate`() =
        runTest {
            callback.onAddingSuppliedItem()
            callback.removingSuppliedItemById(1)

            val state = viewModel.uiState.value

            Truth.assertThat(state.formData).isEqualTo(AddSupplierFormData())
            Truth.assertThat(state.formError).isEqualTo(AddSupplierFormError())
        }

    @Test
    fun `updateListSuppliedItem success should update uistate`() =
        runTest {
            callback.onUpdateListSuppliedItem(AddSupplierSuppliedItem())

            val state = viewModel.uiState.value

            Truth.assertThat(state.formData).isEqualTo(AddSupplierFormData())
        }

    @Test
    fun `submitForm success should call addSupplierUseCase`() =
        runTest {
            val formData = AddSupplierFormData(
                companyName = "company",
                suppliedItem = listOf(AddSupplierSuppliedItem(1, "name", listOf("sku"))),
                country = "country",
                state = "state",
                city = "city",
                zip = 12345,
                companyAddress = "address",
                companyNumber = "98765",
                picName = "pic",
                picNumber = "65432",
                picEmail = "email@mail.com"
            )

            val postBody = PostSupplierRequestBody(
                companyname = "company",
                city = "city",
                country = "country",
                state = "state",
                zipcode = "12345",
                pic = "pic",
                picphone = "65432",
                picemail = "email@mail.com",
                supplieditem = listOf(Supplieditem("name", listOf("sku"))),
                companyphone = "98765",
                companyaddress = "address"
            )

            arrangeAddSupplierUseCase(
                body = postBody,
                result = Result.Success(Unit)
            )

            callback.onUpdateFormData(formData)
            callback.onSubmitForm()

            val state = viewModel.uiState.value

            coVerify {
                addSupplierUseCase(
                    body = postBody
                )
            }

            Truth.assertThat(state.formData).isEqualTo(formData)
            Truth.assertThat(state.isLoadingOverlay).isFalse()
            Truth.assertThat(state.submitState).isTrue()
        }

    @Test
    fun `submitForm success should call updateSupplierByIdUseCase`() =
        runTest {
            val formData = AddSupplierFormData(
                companyName = "company",
                suppliedItem = listOf(AddSupplierSuppliedItem(1, "name", listOf("sku"))),
                country = "country",
                state = "state",
                city = "city",
                zip = 12345,
                companyAddress = "address",
                companyNumber = "98765",
                picName = "pic",
                picNumber = "65432",
                picEmail = "email@mail.com"
            )

            val putBody = PutSupplierRequestBody(
                id = "id",
                companyname = "company",
                city = "city",
                country = "country",
                state = "state",
                zipcode = "12345",
                pic = "pic",
                picphone = "65432",
                picemail = "email@mail.com",
                supplieditem = listOf(PutSupplierRequestBody.Supplieditem("name", listOf("sku"))),
                companyphone = "98765",
                companyaddress = "address"
            )

            arrangeGetSupplierByIdUseCase(
                id = "id",
                result = Result.Success(
                    SupplierEntity(
                        id = "id",
                        companyName = "company",
                        companyPhone = "54321",
                        companyAddress = "address",
                        suppliedItem = listOf(
                            com.example.apiservices.data.model.supplier.AddSupplierSuppliedItem(
                                itemName = "item name",
                                itemSku = listOf("item sku")
                            )
                        ),
                        isActive = "Active",
                        city = "cityy",
                        state = "statee",
                        country = "countryy",
                        zipCode = "65432",
                        pic = "picc",
                        picPhone = "8765",
                        picEmail = "picc@email.com"
                    )
                )
            )

            arrangeUpdateSupplierByIdUseCase(
                body = putBody,
                result = Result.Success(Unit)
            )

            viewModel.init("id")
            callback.onUpdateFormData(formData)
            callback.onSubmitForm()

            val state = viewModel.uiState.value

            coVerify {
                getSupplierByIdUseCase(id = "id")
                updateSupplierByIdUseCase(
                    body = putBody
                )
            }

            Truth.assertThat(state.isEditForm).isTrue()
            Truth.assertThat(state.formData).isEqualTo(formData)
            Truth.assertThat(state.isLoadingOverlay).isFalse()
            Truth.assertThat(state.submitState).isTrue()
        }

    @Test
    fun `formValidation should return true when formdata is empty`() =
        runTest {
            val formError = AddSupplierFormError(
                companyName = "Company Name must not be empty",
                itemName = "You must pick an item name",
                itemSku = "You must pick a SKU",
                country = "You must pick a country",
                state = "You must pick a state",
                city = "You must pick a city",
                zip = "ZIP Code must not be empty",
                companyAddress = "Company Address must not be empty",
                companyNumber = "Company Number must not be empty",
                picName = "PIC Name must not be empty",
                picNumber = "PIC Number must not be empty",
                picEmail = "PIC Email must not be empty"
            )

            callback.onUpdateFormData(AddSupplierFormData())
            callback.onSubmitForm()

            val state = viewModel.uiState.value

            println(state.formError)
            println(formError)
            Truth.assertThat(state.formError).isEqualTo(formError)
            Truth.assertThat(state.formError.hasError()).isTrue()
        }

    @Test
    fun `formValidation should return false when formdata is not empty`() =
        runTest {
            val formData = AddSupplierFormData(
                companyName = "company",
                suppliedItem = listOf(AddSupplierSuppliedItem(1, "name", listOf("sku"))),
                country = "country",
                state = "state",
                city = "city",
                zip = 12345,
                companyAddress = "address",
                companyNumber = "98765",
                picName = "pic",
                picNumber = "65432",
                picEmail = "email@mail.com"
            )

            val postBody = PostSupplierRequestBody(
                companyname = "company",
                city = "city",
                country = "country",
                state = "state",
                zipcode = "12345",
                pic = "pic",
                picphone = "65432",
                picemail = "email@mail.com",
                supplieditem = listOf(Supplieditem("name", listOf("sku"))),
                companyphone = "98765",
                companyaddress = "address"
            )

            arrangeAddSupplierUseCase(
                body = postBody,
                result = Result.Success(Unit)
            )

            callback.onUpdateFormData(formData)
            callback.onSubmitForm()

            val state = viewModel.uiState.value

            Truth.assertThat(state.formError).isEqualTo(AddSupplierFormError())
            Truth.assertThat(state.formError.hasError()).isFalse()
        }

    @Test
    fun `resetFormData success should update uistate`() =
        runTest {
            val expectedResult = AddSuplierUiState(
                formData = AddSupplierFormData(
                    suppliedItem = listOf(
                        AddSupplierSuppliedItem(id = 1)
                    )
                ),
                suppliedItemIndex = 1
            )

            callback.onResetMessageState()

            val state = viewModel.uiState.value

            Truth.assertThat(state).isEqualTo(expectedResult)
            Truth.assertThat(state.suppliedItemIndex).isEqualTo(1)
            Truth.assertThat(state.formData)
                .isEqualTo(AddSupplierFormData(suppliedItem = listOf(AddSupplierSuppliedItem(id = state.suppliedItemIndex))))

        }
}