package com.example.ordertracker.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordertracker.data.repository.OrderRepository
import com.example.ordertracker.orders.Delivery
import com.example.ordertracker.orders.OrderModel
import com.example.ordertracker.orders.Status
import com.example.ordertracker.uistate.OrderDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val orderRepository: OrderRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val orderId: Long = savedStateHandle["orderId"] ?: 0L

    private val _uiState = MutableStateFlow(OrderDetailsUiState())

    val uiState: StateFlow<OrderDetailsUiState> = _uiState.asStateFlow()
    private var originalOrder: OrderModel? = null

    init {
        viewModelScope.launch {
            val order = orderRepository.getOrder(orderId)
            originalOrder = order
            _uiState.update {
                it.copy(
                    order = order,
                    customerName = order?.customerName ?: "",
                    contact = order?.contact ?: "",
                    item = order?.item ?: "",
                    units = order?.units?.toString() ?: "",
                    price = order?.price?.toString() ?: "",
                    delivery = order?.delivery ?: Delivery.DELIVERY,
                    status = order?.status ?: Status.PENDING,
                    isFormValid = true
                )
            }
        }
    }

    fun toggleEditing() {
        _uiState.update { currentState ->
            if (currentState.isEditing) {
                currentState.copy(
                    isEditing = false,
                    order = originalOrder,
                    hasChange = false,
                    customerName = originalOrder?.customerName ?: "",
                    contact = originalOrder?.contact ?: "",
                    item = originalOrder?.item ?: "",
                    units = originalOrder?.units?.toString() ?: "",
                    price = originalOrder?.price?.toString() ?: "",
                    delivery = originalOrder?.delivery ?: Delivery.DELIVERY,
                    status = originalOrder?.status ?: Status.PENDING,
                    customerNameError = null,
                    contactError = null,
                    itemError = null,
                    priceError = null,
                    unitsError = null
                )
            } else {
                currentState.copy(isEditing = true)
            }
        }
    }

    private fun validateCustomerName(name: String): String? =
        if (name.isBlank()) "Full name is required" else null

    private fun validateContact(contact: String): String? =
        if (contact.length < 10) "Enter a valid phone number" else null

    private fun validatePrice(price: String): String? {
        val priceValue = price.toDoubleOrNull()
        return when {
            priceValue == null -> "Enter a valid price"
            priceValue <= 0 -> "Price must be greater than 0"
            else -> null
        }
    }

    private fun validateUnits(units: String): String? {
        val unitsValue = units.toLongOrNull()
        return when {
            unitsValue == null -> "Enter a valid number"
            unitsValue <= 0 -> "Units must be greater than 0"
            else -> null
        }
    }

    private fun updateOrderState() {
        _uiState.update { currentState ->
            val customerNameError = validateCustomerName(currentState.customerName)
            val contactError = validateContact(currentState.contact)
            val priceError = validatePrice(currentState.price)
            val unitsError = validateUnits(currentState.units)

            val isFormValid = customerNameError == null &&
                    contactError == null &&
                    priceError == null &&
                    unitsError == null &&
                    currentState.item.isNotBlank()

            val updatedOrder = currentState.order?.copy(
                customerName = currentState.customerName,
                contact = currentState.contact,
                item = currentState.item,
                units = currentState.units.toLongOrNull() ?: 0L,
                price = currentState.price.toDoubleOrNull() ?: 0.0,
                delivery = currentState.delivery,
                status = currentState.status
            )

            currentState.copy(
                order = updatedOrder,
                customerNameError = customerNameError,
                contactError = contactError,
                priceError = priceError,
                unitsError = unitsError,
                isFormValid = isFormValid,
                hasChange = updatedOrder != originalOrder
            )
        }
    }

    fun updateCustomerName(value: String) {
        _uiState.update { it.copy(customerName = value) }
        updateOrderState()
    }

    fun updateContact(value: String) {
        _uiState.update { it.copy(contact = value) }
        updateOrderState()
    }

    fun updateItem(value: String) {
        _uiState.update { it.copy(item = value) }
        updateOrderState()
    }

    fun updatePrice(value: String) {
        if (value.matches(Regex("^\\d*\\.?\\d*$"))) {
            _uiState.update { it.copy(price = value) }
            updateOrderState()
        }
    }

    fun updateUnits(value: String) {
        if (value.all { it.isDigit() }) {
            _uiState.update { it.copy(units = value) }
            updateOrderState()
        }
    }

    fun updateDelivery(value: Delivery) {
        _uiState.update { it.copy(delivery = value) }
        updateOrderState()
    }

    fun updateStatus(value: Status) {
        _uiState.update { it.copy(status = value) }
        updateOrderState()
    }

    fun saveOrder() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState.hasChange && currentState.isFormValid) {
                currentState.order?.let {
                    orderRepository.updateOrder(it)
                    originalOrder = it
                    _uiState.update { state ->
                        state.copy(hasChange = false, isEditing = false)
                    }
                }
            }
        }
    }
}
