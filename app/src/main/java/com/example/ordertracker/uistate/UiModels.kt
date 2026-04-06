package com.example.ordertracker.uistate

import com.example.ordertracker.orders.Delivery
import com.example.ordertracker.orders.Status

data class CustomerUiModel(
    val id: Long,
    val name: String,
    val contact: String,
    val address: String,
    val email: String,
    val showCustomerDialog: Boolean = false
)

data class OrderUiModel(
    val id: Long,
    val customerName: String,
    val contact: String,
    val item: String,
    val price: String, // Formatted for display
    val units: String,
    val status: Status,
    val statusLabel: String,
    val delivery: Delivery,
    val deliveryLabel: String
)
