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


) {
    val isFormValid: Boolean
        get() = customerNameError == null && contactError == null && emailError == null && addressError == null && customerName.isNotBlank() && contact.isNotBlank() && address.isNotBlank()
}
