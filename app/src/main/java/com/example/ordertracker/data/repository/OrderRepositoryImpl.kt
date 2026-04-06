package com.example.ordertracker.data.repository

import com.example.ordertracker.orders.OrderModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    db: FirebaseFirestore
) : OrderRepository {

    private val _orders = MutableStateFlow<List<OrderModel>>(emptyList())
    override val orders: StateFlow<List<OrderModel>> = _orders.asStateFlow()

    private val orderCollection = db.collection("orders")

    init {
        orderCollection.addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener
            if (snapshot != null) {
                _orders.value = snapshot.toObjects(OrderModel::class.java)
            }
        }
    }

    override suspend fun getOrders(): List<OrderModel> {
        return try {
            val snapshot = orderCollection.get().await()
            snapshot.toObjects(OrderModel::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteOrder(order: OrderModel) {
        orderCollection.document(order.id.toString()).delete().await()
    }

    override suspend fun createOrder(order: OrderModel) {
        orderCollection.document(order.id.toString()).set(order).await()
    }

    override suspend fun getOrder(orderId: Long): OrderModel? {
        return try {
            val snapshot = orderCollection.whereEqualTo("id", orderId).get().await()
            snapshot.toObjects(OrderModel::class.java).firstOrNull()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateOrder(order: OrderModel) {
        orderCollection.document(order.id.toString()).set(order).await()
    }
}
