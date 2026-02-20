package com.example.ordertracker.uistate

import com.example.ordertracker.orders.OrderModel

data class OrderDetailsUiState(
    val order: OrderModel? = null,
    val initialOrder: OrderModel? = null,
    val isEditing: Boolean = false,
) {
    val hasChanged: Boolean
        get() = order != initialOrder
}