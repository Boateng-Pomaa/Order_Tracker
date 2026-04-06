package com.example.ordertracker.orders

data class OrderModel(
    val id: Long = 0L,
    val customerName: String = "",
    val contact: String = "",
    val item: String = "",
    val price: Double = 0.0,
    val units: Long = 0L,
    val status: Status = Status.PENDING,
    val delivery: Delivery = Delivery.DELIVERY,
)