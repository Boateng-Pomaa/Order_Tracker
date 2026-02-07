package com.example.ordertracker.data.repository

import com.example.ordertracker.data.remote.OrderApi
import com.example.ordertracker.orders.OrderModel
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderApi: OrderApi) : OrderRepository {

    override val orders = orderApi.orders

    override suspend fun getOrders(): List<OrderModel> {
        return orderApi.getOrders()
    }

    override suspend fun deleteOrder(order: OrderModel) {
        orderApi.deleteOrder(order)

    }

    override suspend fun createOrder(order: OrderModel) {
        orderApi.createOrder(order)
    }

}