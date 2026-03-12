package com.example.ordertracker.customers

data class CustomerModel(
    val customerName: String,
    val contact: String,
    val address: String,
    val email: String,
    val id: Long
)
