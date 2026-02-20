package com.example.ordertracker.uistate

import com.example.ordertracker.orders.OrderModel
import com.example.ordertracker.orders.Status

sealed interface OrderUiState {
    object Loading : OrderUiState
    data class Success(val orders: List<OrderModel>) : OrderUiState {
        val pendingCount: Int
            get() = orders.count { it.status == Status.PENDING }
    }

    data class Error(val message: String) : OrderUiState
}