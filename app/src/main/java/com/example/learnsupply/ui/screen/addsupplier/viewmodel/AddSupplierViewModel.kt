package com.example.learnsupply.ui.screen.addsupplier.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiservices.base.Result
import com.example.apiservices.data.source.network.model.request.PostSupplierRequestBody
import com.example.apiservices.data.source.network.model.request.PutSupplierRequestBody
import com.example.apiservices.domain.AddSupplierUseCase
import com.example.apiservices.domain.GetSupplierByIdUseCase
import com.example.apiservices.domain.UpdateSupplierByIdUseCase
import com.example.learnsupply.R
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierCallback
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierFormData
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierFormError
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierSuppliedItem
import com.example.learnsupply.ui.screen.addsupplier.uistate.AddSuplierUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddSupplierViewModel @Inject constructor(
    private val getSupplierByIdUseCase: GetSupplierByIdUseCase,
    private val addSupplierUseCase: AddSupplierUseCase,
    private val updateSupplierByIdUseCase: UpdateSupplierByIdUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddSuplierUiState())
    val uiState = _uiState.asStateFlow()

    fun init(id: String? = null) {
        _uiState.value = _uiState.value.copy(
            assetId = "",
            formData = AddSupplierFormData(),
            formError = AddSupplierFormError(),
            submitState = null,
            isEditForm = false
        )

        updateFormData(
            formData = AddSupplierFormData(suppliedItem = listOf(AddSupplierSuppliedItem(id = _uiState.value.suppliedItemIndex))),
        )

        id?.let {
            getSupplierById(id)
        }
    }

    private fun getSupplierById(id: String) {
        _uiState.value = _uiState.value.copy(isLoadingOverlay = true)

        getSupplierByIdUseCase(id).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.update { currData ->
                        currData.copy(
                            isLoadingOverlay = false,
                            formData = AddSupplierFormData(
                                companyName = result.data.companyName,
                                country = result.data.country,
                                state = result.data.state,
                                city = result.data.city,
                                suppliedItem = result.data.suppliedItem.map {
                                    AddSupplierSuppliedItem(
                                        itemName = it.itemName,
                                        itemSku = it.itemSku
                                    )
                                },
                                zip = result.data.zipCode.toInt(),
                                companyAddress = result.data.companyAddress,
                                companyNumber = result.data.companyPhone,
                                picName = result.data.pic,
                                picNumber = result.data.picPhone,
                                picEmail = result.data.picEmail,
                            ),
                            assetId = id,
                            isEditForm = true
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(isLoadingOverlay = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getCallback(): AddSupplierCallback {
        return AddSupplierCallback(
            onUpdateFormData = ::updateFormData,
            onUpdateStayOnForm = ::updateOnStayForm,
            onAddingSuppliedItem = ::addingSuppliedItem,
            removingSuppliedItemById = ::removingSuppliedItemById,
            onUpdateListSuppliedItem = ::updateListSuppliedItem,
            onSubmitForm = ::submitForm,
            onResetMessageState = ::resetFormData,
        )
    }

    private fun updateListSuppliedItem(item: AddSupplierSuppliedItem) {
        val list = _uiState.value.formData.suppliedItem
        _uiState.update { currData ->
            currData.copy(
                formData = currData.formData.copy(
                    suppliedItem = list.map {
                        if (it.id == item.id) {
                            item
                        } else {
                            it
                        }
                    }
                )
            )
        }
    }

    private fun addingSuppliedItem() {
        _uiState.value = _uiState.value.copy(
            suppliedItemIndex = _uiState.value.suppliedItemIndex + 1
        )
        val updatedSuppliedItem = _uiState.value.formData.suppliedItem.toMutableList().apply {
            add(AddSupplierSuppliedItem(id = _uiState.value.suppliedItemIndex))
        }

        updateFormData(
            _uiState.value.formData.copy(suppliedItem = updatedSuppliedItem)
        )
    }

    private fun removingSuppliedItemById(id: Int) {
        val updatedSuppliedItem = _uiState.value.formData.suppliedItem.filter { it.id != id }

        updateFormData(
            _uiState.value.formData.copy(suppliedItem = updatedSuppliedItem)
        )
    }

    private fun updateFormData(formData: AddSupplierFormData) {
        _uiState.value = _uiState.value.copy(
            formData = formData,
            formError = AddSupplierFormError()
        )
    }

    private fun submitForm() {
        val data = _uiState.value.formData

        if (!formValidation(data)) return

        _uiState.value = _uiState.value.copy(isLoadingOverlay = true)

        val domain = if (_uiState.value.isEditForm) {
            updateSupplierByIdUseCase(
                PutSupplierRequestBody(
                    id = _uiState.value.assetId,
                    companyname = data.companyName,
                    city = data.city,
                    country = data.country,
                    state = data.state,
                    zipcode = data.zip.toString(),
                    pic = data.picName,
                    picphone = data.picNumber,
                    picemail = data.picEmail,
                    supplieditem = data.suppliedItem.map {
                        PutSupplierRequestBody.Supplieditem(
                            item = it.itemName,
                            sku = it.itemSku
                        )
                    },
                    companyphone = data.companyNumber,
                    companyaddress = data.companyAddress
                )
            )
        } else {
            addSupplierUseCase(
                PostSupplierRequestBody(
                    companyname = data.companyName,
                    city = data.city,
                    country = data.country,
                    state = data.state,
                    zipcode = data.zip.toString(),
                    pic = data.picName,
                    picphone = data.picNumber,
                    picemail = data.picEmail,
                    supplieditem = data.suppliedItem.map {
                        PostSupplierRequestBody.Supplieditem(
                            item = it.itemName,
                            sku = it.itemSku
                        )
                    },
                    companyphone = data.companyNumber,
                    companyaddress = data.companyAddress
                )
            )
        }

        domain.onEach { result ->
            _uiState.update { currData ->
                currData.copy(
                    isLoadingOverlay = false,
                    submitState = result is Result.Success
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun formValidation(data: AddSupplierFormData): Boolean {
        var formError = AddSupplierFormError()
        if (data.companyName.isEmpty()) {
            formError =
                formError.copy(companyName = context.getString(R.string.message_error_empty_company_name))
        }

        if (data.suppliedItem.isEmpty() || data.suppliedItem.any { it.itemName.isEmpty() }) {
            formError =
                formError.copy(itemName = context.getString(R.string.message_error_empty_item_name))
        }

        if (data.suppliedItem.isEmpty() || data.suppliedItem.any { it.itemSku.isEmpty() }) {
            formError =
                formError.copy(itemSku = context.getString(R.string.message_error_empty_item_sku))
        }

        if (data.country.isEmpty()) {
            formError =
                formError.copy(country = context.getString(R.string.message_error_empty_country))
        }

        if (data.state.isEmpty()) {
            formError =
                formError.copy(state = context.getString(R.string.message_error_empty_state))
        }

        if (data.city.isEmpty()) {
            formError =
                formError.copy(city = context.getString(R.string.message_error_empty_city))
        }

        if (data.zip == 0) {
            formError =
                formError.copy(zip = context.getString(R.string.message_error_empty_zip))
        }

        if (data.companyAddress.isEmpty()) {
            formError =
                formError.copy(companyAddress = context.getString(R.string.message_error_empty_company_address))
        }

        if (data.companyNumber.isEmpty()) {
            formError =
                formError.copy(companyNumber = context.getString(R.string.message_error_empty_company_number))
        }

        if (data.picName.isEmpty()) {
            formError =
                formError.copy(picName = context.getString(R.string.message_error_empty_pic_name))
        }

        if (data.picNumber.isEmpty()) {
            formError =
                formError.copy(picNumber = context.getString(R.string.message_error_empty_pic_number))
        }

        if (data.picEmail.isEmpty()) {
            formError =
                formError.copy(picEmail = context.getString(R.string.message_error_empty_pic_email))
        }

        _uiState.value = _uiState.value.copy(formError = formError)

        return !formError.hasError()
    }

    private fun updateOnStayForm() {
        _uiState.update { currData ->
            currData.copy(isStayOnForm = !currData.isStayOnForm)
        }
    }

    private fun resetFormData() {
        _uiState.value = AddSuplierUiState()
        addingSuppliedItem()
    }
}