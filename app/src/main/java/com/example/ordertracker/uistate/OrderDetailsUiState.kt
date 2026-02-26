package com.example.ordertracker.uistate

import com.example.ordertracker.orders.Delivery
import com.example.ordertracker.orders.OrderModel
import com.example.ordertracker.orders.Status

data class OrderDetailsUiState(
    val order: OrderModel? = null,

    val customerName: String = "",
    val contact:String = "",
    val item: String = "",
    val units: String = "",
    val price: String = "",
    val delivery: Delivery = Delivery.DELIVERY,
    val status: Status = Status.PENDING,


    val isEditing: Boolean = false,
    val hasChange:Boolean = false,

    val error: String? = null,

    val customerNameError: String? = null,
    val contactError: String? = null,
    val itemError: String? = null,
    val priceError: String? = null,
    val unitsError: String? = null,

    val isFormValid: Boolean = false
)