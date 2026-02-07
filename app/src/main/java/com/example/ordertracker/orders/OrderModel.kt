package com.example.ordertracker.orders

data class OrderModel(
    val id: Long,
    val customerName: String,
    val contact: String,
    val item: String,
    val price: Double,
    val units: Long,
    val status: Status,
    val delivery: Delivery,
)