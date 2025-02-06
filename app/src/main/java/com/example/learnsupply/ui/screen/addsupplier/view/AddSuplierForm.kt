package com.example.learnsupply.ui.screen.addsupplier.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.learnsupply.R
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierFormData
import com.example.learnsupply.ui.screen.addsupplier.model.AddSupplierSuppliedItem
import com.example.learnsupply.ui.screen.addsupplier.uistate.AddSuplierUiState
import com.tagsamurai.tscomponents.button.Button
import com.tagsamurai.tscomponents.button.MultiSelector
import com.tagsamurai.tscomponents.button.OptionData
import com.tagsamurai.tscomponents.button.SingleSelector
import com.tagsamurai.tscomponents.model.TypeButton
import com.tagsamurai.tscomponents.shimmerEffect.ShimmerEffect
import com.tagsamurai.tscomponents.textfield.NumberTextField
import com.tagsamurai.tscomponents.textfield.PhoneNumberTextField
import com.tagsamurai.tscomponents.textfield.TextField
import com.tagsamurai.tscomponents.theme.theme
import com.tagsamurai.tscomponents.utils.Spacer.widthBox

@Composable
fun AddSuplierForm(
    uiState: AddSuplierUiState,
    onUpdateForm: (AddSupplierFormData) -> Unit,
    onAddingSuppliedItem: () -> Unit,
    removingSuppliedItemById: (Int) -> Unit,
    onUpdateListSuppliedItem: (AddSupplierSuppliedItem) -> Unit
) {

    var isReset by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.formData) {
        isReset = uiState.formData == AddSupplierFormData() && !isReset
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (uiState.isLoadingFormOption) {
            ShimmerEffect(width = 180.dp, height = 24.dp)
            ShimmerEffect(width = 180.dp, height = 24.dp)
            ShimmerEffect(width = 180.dp, height = 24.dp)
            ShimmerEffect(width = 180.dp, height = 24.dp)
        } else {
            TextField(
                title = stringResource(R.string.company_name),
                value = uiState.formData.companyName,
                onValueChange = { result ->
                    onUpdateForm(
                        uiState.formData.copy(
                            companyName = result
                        )
                    )
                },
                placeholder = "Enter company name",
                textError = uiState.formError.companyName,
                isError = uiState.formError.companyName != null,
                maxChar = 30,
                required = true
            )
            Button(
                onClick = { onAddingSuppliedItem() },
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.supplied_item),
                type = TypeButton.OUTLINED,
                leadingIcon = com.tagsamurai.tscomponents.R.drawable.ic_add_fill_24dp
            )
            uiState.formData.suppliedItem.forEach { item ->
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        Modifier.weight(3f)
                    ) {
                        SingleSelector(
                            onValueChange = { result ->
                                onUpdateListSuppliedItem(
                                    AddSupplierSuppliedItem(
                                        id = item.id,
                                        itemName = result,
                                        itemSku = item.itemSku
                                    )
                                )
                            },
                            items = listOf(
                                OptionData("Laptop", "Laptop"),
                                OptionData("Kulkas", "Kulkas"),
                                OptionData("TV", "TV"),
                                OptionData("AC", "AC"),
                            ),
                            placeHolder = stringResource(R.string.title_item_name),
                            value = item.itemName,
                            title = stringResource(R.string.title_item_name),
                            required = true,
                            isError = uiState.formError.itemName != null,
                            textError = uiState.formError.itemName
                        )
                    }
                    10.widthBox()
                    Box(
                        Modifier.weight(3f)
                    ) {
                        MultiSelector(
                            onValueChange = { result ->
                                onUpdateListSuppliedItem(
                                    AddSupplierSuppliedItem(
                                        id = item.id,
                                        itemName = item.itemName,
                                        itemSku = result.map { it }
                                    )
                                )

                            },
                            items = listOf(
                                OptionData("SFD-13817386", "SFD-13817386"),
                                OptionData("SFD-1381735", "SFD-1381735"),
                                OptionData("SFD-13817384", "SFD-13817384"),
                                OptionData("SFD-13817383", "SFD-13817383"),
                                OptionData("SFD-13817382", "SFD-13817382"),
                                OptionData("SFD-13817381", "SFD-13817381"),
                            ),
                            placeHolder = stringResource(R.string.sku),
                            value = item.itemSku,
                            title = stringResource(R.string.sku),
                            required = true,
                            isError = uiState.formError.itemSku != null,
                            textError = uiState.formError.itemSku,
                            isUseChip = true,
                            enabled = item.itemName.isNotBlank()
                        )
                    }
                    if (uiState.formData.suppliedItem.size > 1) {
                        10.widthBox()
                        Box(
                            Modifier.weight(1f)
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 22.dp),
                                type = TypeButton.OUTLINED,
                                content = {
                                    Icon(
                                        tint = theme.buttonDanger,
                                        painter = painterResource(com.tagsamurai.tscomponents.R.drawable.ic_subtract_line_24dp),
                                        contentDescription = "Subtract Supplied Item",
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                onClick = { removingSuppliedItemById(item.id) }
                            )
                        }
                    }

                }
            }

            SingleSelector(
                onValueChange = { result ->
                    onUpdateForm(
                        uiState.formData.copy(
                            country = result
                        )
                    )
                },
                items = listOf(
                    OptionData("Indonesia", "Indonesia"),
                    OptionData("Singapore", "Singapore"),
                    OptionData("Malaysia", "Malaysia"),
                    OptionData("Thailand", "Thailand"),
                ),
                placeHolder = stringResource(R.string.country),
                value = uiState.formData.country,
                title = stringResource(R.string.country),
                required = true,
                isError = uiState.formError.country != null,
                textError = uiState.formError.country
            )
            SingleSelector(
                onValueChange = { result ->
                    onUpdateForm(
                        uiState.formData.copy(
                            state = result
                        )
                    )
                },
                items = listOf(
                    OptionData("DKI Jakarta", "DKI Jakarta"),
                    OptionData("Jawa Barat", "Jawa Barat"),
                    OptionData("Jawa Tengah", "Jawa Tengah"),
                    OptionData("Jawa Timur", "Jawa Timur"),
                ),
                placeHolder = stringResource(R.string.state),
                value = uiState.formData.state,
                title = stringResource(R.string.state),
                required = true,
                isError = uiState.formError.state != null,
                textError = uiState.formError.state
            )
            SingleSelector(
                onValueChange = { result ->
                    onUpdateForm(
                        uiState.formData.copy(
                            city = result
                        )
                    )
                },
                items = listOf(
                    OptionData("Jakarta Utara", "Jakarta Utara"),
                    OptionData("Jakarta Selatan", "Jakarta Selatan"),
                    OptionData("Jakarta Barat", "Jakarta Barat"),
                    OptionData("Jakarta Timur", "Jakarta Timur"),
                ),
                placeHolder = stringResource(R.string.city),
                value = uiState.formData.city,
                title = stringResource(R.string.city),
                required = true,
                isError = uiState.formError.city != null,
                textError = uiState.formError.city
            )
            NumberTextField(
                title = stringResource(R.string.zip_code),
                value = uiState.formData.zip,
                onValueChange = { result ->
                    onUpdateForm(
                        uiState.formData.copy(
                            zip = result ?: 0
                        )
                    )
                },
                placeholder = "Enter ZIP Code",
                isError = uiState.formError.zip != null,
                textError = uiState.formError.zip,
                required = true
            )
            TextField(
                title = stringResource(R.string.company_address),
                value = uiState.formData.companyAddress,
                onValueChange = { result ->
                    onUpdateForm(
                        uiState.formData.copy(
                            companyAddress = result
                        )
                    )
                },
                placeholder = "Enter company address",
                textError = uiState.formError.companyAddress,
                isError = uiState.formError.companyAddress != null,
                maxChar = 120,
                required = true
            )
            val phoneCompany = uiState.formData.companyNumber.split(" ")
            PhoneNumberTextField(
                onValueChange = { dialCode, value ->
                    val result = "$dialCode $value"
                    onUpdateForm(
                        uiState.formData.copy(
                            companyNumber = result
                        )
                    )
                },
                dialCode = phoneCompany.first(),
                value = phoneCompany.last(),
                title = stringResource(R.string.company_phone_number),
                placeholder = "Enter company phone number",
                textError = uiState.formError.companyNumber,
                isError = uiState.formError.companyNumber != null,
                required = true
            )
            TextField(
                title = stringResource(R.string.pic_name),
                value = uiState.formData.picName,
                onValueChange = { result ->
                    onUpdateForm(
                        uiState.formData.copy(
                            picName = result
                        )
                    )
                },
                placeholder = "Enter PIC Name",
                textError = uiState.formError.picName,
                isError = uiState.formError.picName != null,
                maxChar = 30,
                required = true
            )

            val phonePic = uiState.formData.picNumber.split(" ")
            PhoneNumberTextField(
                onValueChange = { dialCode, value ->
                    val result = "$dialCode $value"
                    onUpdateForm(
                        uiState.formData.copy(
                            picNumber = result
                        )
                    )
                },
                dialCode = phonePic.first(),
                value = phonePic.last(),
                title = stringResource(R.string.pic_contact_number),
                placeholder = "Enter PIC Contact Number",
                textError = uiState.formError.picNumber,
                isError = uiState.formError.picNumber != null,
                required = true
            )

            TextField(
                title = stringResource(R.string.pic_email),
                value = uiState.formData.picEmail,
                onValueChange = { result ->
                    onUpdateForm(
                        uiState.formData.copy(
                            picEmail = result
                        )
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                placeholder = "Enter PIC Email",
                textError = uiState.formError.picEmail,
                isError = uiState.formError.picEmail != null,
                maxChar = 30,
                required = true
            )
        }
    }
}