package com.example.ordertracker.data.repository

import com.example.ordertracker.customers.CustomerModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    db: FirebaseFirestore,
) : CustomerRepository {

    private val _customers = MutableStateFlow<List<CustomerModel>>(emptyList())
    override val customers: StateFlow<List<CustomerModel>> = _customers.asStateFlow()

    private val customerCollection = db.collection("customers")

    init {
        customerCollection.addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener
            if (snapshot != null) {
                _customers.value = snapshot.toObjects(CustomerModel::class.java)
            }
        }
    }

    override suspend fun getCustomers(): List<CustomerModel> {
        return try {
            val snapshot = customerCollection.get().await()
            snapshot.toObjects(CustomerModel::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getCustomer(customerId: Long): CustomerModel? {
        return try {
            val snapshot = customerCollection.whereEqualTo("id", customerId).get().await()
            snapshot.toObjects(CustomerModel::class.java).firstOrNull()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun createCustomer(customer: CustomerModel) {
        customerCollection.document(customer.id.toString()).set(customer).await()
    }

    override suspend fun deleteCustomer(customer: CustomerModel) {
        customerCollection.document(customer.id.toString()).delete().await()
    }

    override suspend fun updateCustomer(customer: CustomerModel) {
        customerCollection.document(customer.id.toString()).set(customer).await()
    }
}
