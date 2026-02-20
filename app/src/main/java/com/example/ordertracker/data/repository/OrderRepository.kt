package com.example.ordertracker.data.repository

import com.example.ordertracker.orders.OrderModel
import kotlinx.coroutines.flow.StateFlow

interface OrderRepository {
    val orders: StateFlow<List<OrderModel>>

   suspend fun getOrders(): List<OrderModel>


   suspend fun  deleteOrder(order: OrderModel)
    suspend fun createOrder(order: OrderModel)

    suspend fun getOrder(orderId: Long): OrderModel?

    suspend fun updateOrder(order: OrderModel)

}
