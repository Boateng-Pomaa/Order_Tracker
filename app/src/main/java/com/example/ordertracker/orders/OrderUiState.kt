package com.example.ordertracker.orders

sealed interface OrderUiState {
    object Loading : OrderUiState
    data class Success(val orders: List<OrderModel>) : OrderUiState {
        val pendingCount: Int
            get() = orders.count { it.status == Status.PENDING }
    }

    data class Error(val message: String) : OrderUiState
}


