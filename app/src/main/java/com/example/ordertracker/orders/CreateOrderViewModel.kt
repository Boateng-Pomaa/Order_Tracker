package com.example.ordertracker.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordertracker.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateOrderViewModel @Inject constructor(private val orderRepository: OrderRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(CreateOrderUiState())
    val uiState: StateFlow<CreateOrderUiState> = _uiState

    fun onCustomerNameChange(value: String) {
        val error = validateCustomerName(value)
        _uiState.update {
            it.copy(
                customerNameError = error,
                customerName = value,
                isFormValid = isFormValid(it.copy(customerName = value, customerNameError = error))
            )
        }
    }

    fun onContactChange(value: String) {

        val error = validateContact(value)
        _uiState.update {
            it.copy(
                contact = value, contactError = error, isFormValid = isFormValid(
                    it.copy(
                        contact = value, contactError = error
                    )
                )

            )
        }
    }

    fun onItemChange(value: String) {
        _uiState.value = _uiState.value.copy(item = value)
    }

    fun onUnitsChange(value: String) {
        _uiState.update {
            val error = validateUnits(value)
            it.copy(
                unitsError = error,
                units = value,
                isFormValid = isFormValid(it.copy(units = value, unitsError = error))
            )
        }
    }

    fun onPriceChange(value: String) {
        _uiState.update {
            val error = validatePrice(value)
            it.copy(
                priceError = error,
                price = value,
                isFormValid = isFormValid(it.copy(price = value, priceError = error))
            )

        }
    }

    fun onDeliveryChange(delivery: Delivery) {
        _uiState.value = _uiState.value.copy(delivery = delivery)
    }

    fun onStatusChange(status: Status) {
        _uiState.value = _uiState.value.copy(status = status)
    }


    fun createOrder() {
        viewModelScope.launch {
            try {
                val state = _uiState.value

                val order = OrderModel(
                    id = System.currentTimeMillis(),
                    customerName = state.customerName,
                    item = state.item,
                    units = state.units.toLong(),
                    price = state.price.toDouble(),
                    status = state.status,
                    delivery = state.delivery,
                    contact = state.contact
                )

                orderRepository.createOrder(order)
                _uiState.value = _uiState.value.copy(showSuccessDialog = true)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Invalid input")
            }
        }
    }

    fun onDismissSuccessDialog() {
        _uiState.value = _uiState.value.copy(showSuccessDialog = false)
    }

    private fun isFormValid(state: CreateOrderUiState): Boolean {
        return listOf(
            state.customerNameError,
            state.contactError,
            state.itemError,
            state.priceError,
            state.unitsError
        ).all { it == null } && state.customerName.isNotBlank() && state.contact.isNotBlank() && state.item.isNotBlank()
    }


    private fun validateCustomerName(name: String): String? =
        if (name.isBlank()) "Full name is required" else null

    private fun validateContact(contact: String): String? =
        if (contact.length < 10) "Enter a valid phone number" else null

    private fun validatePrice(price: String): String = price.toDoubleOrNull()?.let {
        if (it <= 0) "Price must be greater than 0" else null
    } ?: "Enter a valid price"

    private fun validateUnits(units: String): String = units.toIntOrNull()?.let {
        if (it <= 0) "Units must be at least 1" else null
    } ?: "Enter a valid number"


}