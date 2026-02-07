package com.example.ordertracker.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordertracker.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateOrderViewModel @Inject constructor(private val orderRepository: OrderRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(CreateOrderUiState())
    val uiState: StateFlow<CreateOrderUiState> = _uiState

    fun onCustomerNameChange(value: String) {
        _uiState.value = _uiState.value.copy(customerName = value)
    }

    fun onContactChange(value: String) {
        _uiState.value = _uiState.value.copy(contact = value)
    }

    fun onItemChange(value: String) {
        _uiState.value = _uiState.value.copy(item = value)
    }

    fun onUnitsChange(value: String) {
        _uiState.value = _uiState.value.copy(units = value)
    }

    fun onPriceChange(value: String) {
        _uiState.value = _uiState.value.copy(price = value)
    }

    fun onDeliveryChange(delivery: Delivery) {
        _uiState.value = _uiState.value.copy(delivery = delivery)
    }

    fun onStatusChange(status: Status) {
        _uiState.value = _uiState.value.copy(status = status)
    }


    fun createOrder(onSuccess: () -> Unit) {
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

}