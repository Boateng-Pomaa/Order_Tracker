package com.example.ordertracker.uistate

import com.example.ordertracker.orders.Delivery
import com.example.ordertracker.orders.Status

data class CreateOrderUiState(
    val customerName: String = "",
    val contact: String = "",
    val item: String = "",
    val units: String = "",
    val price: String = "",
    val delivery: Delivery = Delivery.DELIVERY,
    val status: Status = Status.PENDING,
    val isSaving: Boolean = false,
    val error: String? = null,

    val customerNameError: String? = null,
    val contactError: String? = null,
    val itemError: String? = null,
    val priceError: String? = null,
    val unitsError: String? = null,
    val showSuccessDialog: Boolean = false
) {
    val isFormValid: Boolean
        get() = customerName.isNotBlank() && contact.isNotBlank() && item.isNotBlank() && price.isNotBlank() && units.isNotBlank() && customerNameError == null && contactError == null && itemError == null && priceError == null && unitsError == null
}
