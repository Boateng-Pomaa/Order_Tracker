package com.example.ordertracker.uistate

data class CreateCustomerUiState(
    val customerName: String = "",
    val contact: String = "",
    val email: String = "",
    val address: String = "",
    val customerNameError: String? = null,
    val contactError: String? = null,
    val emailError: String? = null,
    val addressError: String? = null,
    val error: String? = null,
    val showSuccessDialog: Boolean = false,
    val showErrorDialog: Boolean = false


    ){
    val isFormValid: Boolean
        get() = customerName.isNotBlank() && contact.isNotBlank() && email.isNotBlank() && address.isNotBlank()
}