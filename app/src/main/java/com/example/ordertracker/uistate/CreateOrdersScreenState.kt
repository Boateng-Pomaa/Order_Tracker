package com.example.ordertracker.uistate

data class CreateOrdersScreenState(
    val orders: List<CreateOrderUiState> = listOf(CreateOrderUiState()),
    val showSuccessDialog: Boolean = false,
    val error: String? = null
)