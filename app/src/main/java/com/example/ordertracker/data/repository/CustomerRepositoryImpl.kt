package com.example.ordertracker.data.repository

import com.example.ordertracker.customers.CustomerModel
import com.example.ordertracker.data.remote.CustomerApi
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val customerApi: CustomerApi

) : CustomerRepository {
    override val customers = customerApi.customers


    override suspend fun getCustomers(): List<CustomerModel> {
        return customerApi.getCustomers()
    }

    override suspend fun getCustomer(customerId: Long): CustomerModel? {
        return customerApi.getCustomer(customerId)

    }

    override suspend fun createCustomer(customer: CustomerModel) {
        customerApi.createCustomer(customer)
    }

    override suspend fun deleteCustomer(customer: CustomerModel) {
        customerApi.deleteCustomer(customer)
    }

    override suspend fun updateCustomer(customer: CustomerModel) {
        customerApi.updateCustomer(customer)

    }


}