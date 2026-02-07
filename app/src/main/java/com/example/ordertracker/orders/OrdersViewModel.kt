package com.example.ordertracker.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordertracker.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val orderRepository: OrderRepository) :
    ViewModel() {

    val uiState: StateFlow<OrderUiState> =
        orderRepository.orders.map { OrderUiState.Success(it) as OrderUiState }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = OrderUiState.Loading
            )

    fun deleteOrder(order: OrderModel) {
        viewModelScope.launch {
            orderRepository.deleteOrder(order)
        }
    }
}