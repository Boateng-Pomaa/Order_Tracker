package com.example.ordertracker.data.remote

import com.example.ordertracker.orders.Delivery
import com.example.ordertracker.orders.OrderModel
import com.example.ordertracker.orders.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OrdersList : OrderApi {


    private val _orders = MutableStateFlow(
        listOf(
            OrderModel(
                id = 1,
                customerName = "Eden",
                item = "Tv Console",
                price = 800.0,
                units = 1,
                status = Status.PICKED,
                delivery = Delivery.DELIVERY,
                contact = "00987654"
            ), OrderModel(
                id = 2,
                customerName = "Kwaku",
                item = "Gaming Chair Red",
                price = 100.0,
                units = 1,
                status = Status.PENDING,
                delivery = Delivery.SELF_PICKUP,
                contact = "00987654"
            ), OrderModel(
                id = 3,
                customerName = "Justice",
                item = "Adjustable Table",
                price = 1000.0,
                units = 1,
                status = Status.DELIVERED,
                delivery = Delivery.DELIVERY,
                contact = "00987654"
            ), OrderModel(
                id = 4,
                customerName = "Bismark",
                item = "Adjustable Table",
                price = 1500.0,
                units = 1,
                status = Status.PENDING,
                delivery = Delivery.DELIVERY,
                contact = "00987654"
            ),
            OrderModel(
                id = 5,
                customerName = "Cliff",
                item = "Swivel Chair",
                price = 1500.0,
                units = 1,
                status = Status.PENDING,
                delivery = Delivery.SELF_PICKUP,
                contact = "097666555"
            ),
            OrderModel(
                id = 6,
                customerName = "Lin",
                item = "Swivel Chair",
                price = 1500.0,
                units = 1,
                status = Status.PENDING,
                delivery = Delivery.DELIVERY,
                contact = "012334566"
            )
        )
    )

    override val orders: StateFlow<List<OrderModel>> = _orders

    override suspend fun getOrders(): List<OrderModel> {
        return _orders.value

    }

    override suspend fun createOrder(order: OrderModel) {
        _orders.value += order
    }

    override suspend fun getPendingOrders(): List<OrderModel> {
        return _orders.value.filter { it.status == Status.PENDING }
    }

    override suspend fun deleteOrder(order: OrderModel) {
        _orders.value = _orders.value.filterNot { it.id == order.id }

    }

    override suspend fun getOrder(orderId: Long): OrderModel? {
        return _orders.value.find { it.id == orderId }

    }

    override suspend fun updateOrder(order: OrderModel) {
        val currentOrders = _orders.value.toMutableList()
        val index = currentOrders.indexOfFirst { it.id == order.id }
        if (index != -1) {
            currentOrders[index] = order
            _orders.value = currentOrders
        }
    }
}