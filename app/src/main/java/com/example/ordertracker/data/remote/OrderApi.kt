package com.example.ordertracker.data.remote

import com.example.ordertracker.orders.OrderModel
import kotlinx.coroutines.flow.StateFlow

interface OrderApi {
    val orders: StateFlow<List<OrderModel>>

    suspend fun getOrders(): List<OrderModel>

    suspend fun createOrder(order: OrderModel)

    suspend fun getPendingOrders(): List<OrderModel>

    suspend fun deleteOrder(order: OrderModel)
}


