package com.example.ordertracker.orders

data class CreateOrderUiState(
    val customerName: String = "",
    val contact:String = "",
    val item: String = "",
    val units: String = "",
    val price: String = "",
    val delivery: Delivery = Delivery.DELIVERY,
    val status:Status = Status.PENDING,
    val isSaving: Boolean = false,
    val error: String? = null
)
