package com.example.learnsupply.ui.screen.addsupplier.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiservices.base.Result
import com.example.apiservices.data.source.network.model.request.PostSupplierRequestBody
import com.example.apiservices.domain.AddSupplierUseCase
import com.example.apiservices.domain.GetSupplierByIdUseCase
import com.example.apiservices.domain.GetSupplierUseCase
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
    private val getSupplierUseCase: GetSupplierUseCase,
    private val addSupplierUseCase: AddSupplierUseCase,
//    private val updateSupplierByIdUseCase: UpdateSupplierByIdUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddSuplierUiState())
    val uiState = _uiState.asStateFlow()

    fun init(id: String? = null) {
//        updateListSuppliedItem(AddSupplierSuppliedItem())
        updateFormData(
            formData = AddSupplierFormData(suppliedItem = listOf(AddSupplierSuppliedItem(id = _uiState.value.suppliedItemIndex))),
//            suppliedItem = AddSupplierSuppliedItem()
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
//                                itemName = result.data.suppliedItemName.first(),
//                                itemSku = result.data.suppliedItemSku,
                                country = result.data.country,
                                state = result.data.state,
                                city = result.data.city
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

        val body = PostSupplierRequestBody(
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
            } ,
            companyphone = data.companyNumber,
            companyaddress = data.companyAddress
        )
        val domain = addSupplierUseCase(body)
//        if (_uiState.value.isEditForm) {
//            updateSupplierByIdUseCase(_uiState.value.assetId, body)
//        } else {
//            addSupplierUseCase(body)
//        }

        domain.onEach { result ->
            _uiState.update { currData ->
                currData.copy(
                    isLoadingOverlay = false,
                    submitState = result is Result.Success
                )
            }
        }.launchIn(viewModelScope)

//        resetFormData()
    }

    private fun formValidation(data: AddSupplierFormData): Boolean {
        var formError = AddSupplierFormError()
//        if (data.group.isEmpty()) {
//            formError =
//                formError.copy(group = context.getString(R.string.message_error_empty_group))
//        }
//
//        if (data.name.isEmpty()) {
//            formError =
//                formError.copy(name = context.getString(R.string.message_error_empty_name))
//        }
//
//        if (data.brand.isEmpty()) {
//            formError =
//                formError.copy(brand = context.getString(R.string.message_error_empty_brand))
//        }
//
//        if (data.model.isEmpty()) {
//            formError =
//                formError.copy(model = context.getString(R.string.message_error_empty_model))
//        }

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