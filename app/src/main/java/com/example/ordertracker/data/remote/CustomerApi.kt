package com.example.ordertracker.data.remote

import com.example.ordertracker.customers.CustomerModel
import kotlinx.coroutines.flow.StateFlow

interface CustomerApi {
    val customers: StateFlow<List<CustomerModel>>
    suspend fun getCustomers(): List<CustomerModel>
    suspend fun createCustomer(customer: CustomerModel)
    suspend fun getCustomer(customerId: Long): CustomerModel?
    suspend fun deleteCustomer(customer: CustomerModel)
    suspend fun updateCustomer(customer: CustomerModel)

}