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
            _uiState.value = OrderDetailsUiState(order = order)
        }
    }

    fun toggleEditing() {
        _uiState.update {
            if (it.isEditing) {
                it.copy(isEditing = false, order = originalOrder, hasChange = false)
            } else {
                it.copy(isEditing = true)
            }
        }
    }

    private fun updateOrder(updateBlock: (OrderModel) -> OrderModel) {
        _uiState.update { currentState ->
            val updatedOrder = currentState.order?.let(updateBlock)
            currentState.copy(
                order = updatedOrder, hasChange = updatedOrder != originalOrder
            )
        }
    }


    fun updateCustomerName(value: String) {
        updateOrder { it.copy(customerName = value) }
    }

    fun updateContact(value: String) {
        updateOrder { it.copy(contact = value) }
    }

    fun updateItem(value: String) {
        updateOrder { it.copy(item = value) }
    }

    fun updatePrice(value: String) {
        updateOrder { it.copy(price = value.toDoubleOrNull() ?: 0.0) }
    }

    fun updateUnits(value: String) {
        updateOrder { it.copy(units = value.toLongOrNull() ?: 0) }
    }

    fun updateDelivery(value: Delivery) {
        updateOrder { it.copy(delivery = value) }
    }

    fun updateStatus(value: Status) {
        updateOrder { it.copy(status = value) }
    }

    fun saveOrder() {
        viewModelScope.launch {
            if (uiState.value.hasChange) {
                uiState.value.order?.let {
                    orderRepository.updateOrder(it)
                    originalOrder = it
                    _uiState.update { currentState ->
                        currentState.copy(hasChange = false)
                    }
                }
            }
        }
    }
}