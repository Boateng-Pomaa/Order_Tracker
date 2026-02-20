package com.example.ordertracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordertracker.data.repository.OrderRepository
import com.example.ordertracker.orders.OrderModel
import com.example.ordertracker.uistate.OrderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val orderRepository: OrderRepository) :
    ViewModel() {
    private val _orderToDelete = MutableStateFlow<OrderModel?>(null)
    val orderToDelete: StateFlow<OrderModel?> = _orderToDelete


    val uiState: StateFlow<OrderUiState> =
        orderRepository.orders.map { OrderUiState.Success(it) as OrderUiState }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = OrderUiState.Loading
        )


    fun requestDelete(order: OrderModel) {
        _orderToDelete.value = order
    }

    fun confirmDelete() {
        val order = _orderToDelete.value ?: return

        viewModelScope.launch {
            orderRepository.deleteOrder(order)
            _orderToDelete.value = null
        }
    }

    fun cancelDelete() {
        _orderToDelete.value = null
    }


}