package com.example.ordertracker.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordertracker.data.repository.OrderRepository
import com.example.ordertracker.orders.Delivery
import com.example.ordertracker.orders.Status
import com.example.ordertracker.uistate.OrderDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val orderRepository: OrderRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val orderId: Long =
        savedStateHandle["orderId"] ?: 0L

    private val _uiState =
        MutableStateFlow(OrderDetailsUiState())

    val uiState: StateFlow<OrderDetailsUiState> =
        _uiState

    init {
        viewModelScope.launch {
            val order = orderRepository.getOrder(orderId)
            _uiState.value = OrderDetailsUiState(order = order, initialOrder = order)
        }
    }

    fun toggleEditing() {
        _uiState.update {
            it.copy(isEditing = !it.isEditing)
        }
    }


    fun updateCustomerName(value: String) {
        _uiState.update {
            it.copy(order = it.order?.copy(customerName = value))
        }
    }

    fun updateContact(value: String) {
        _uiState.update {
            it.copy(order = it.order?.copy(contact = value))
        }
    }

    fun updateItem(value: String) {
        _uiState.update {
            it.copy(order = it.order?.copy(item = value))
        }
    }

    fun updatePrice(value: String) {
        _uiState.update {
            it.copy(order = it.order?.copy(price = value.toDoubleOrNull() ?: 0.0))
        }
    }

    fun updateUnits(value: String) {
        _uiState.update {
            it.copy(order = it.order?.copy(units = value.toLongOrNull() ?: 0))
        }
    }

    fun updateDelivery(value: Delivery) {
        _uiState.update {
            it.copy(order = it.order?.copy(delivery = value))
        }
    }

    fun updateStatus(value: Status) {
        _uiState.update {
            it.copy(order = it.order?.copy(status = value))
        }
    }

    fun saveOrder() {
        viewModelScope.launch {
            if (uiState.value.hasChanged) {
                uiState.value.order?.let {
                    orderRepository.updateOrder(it)
                }
            }
        }
    }
}