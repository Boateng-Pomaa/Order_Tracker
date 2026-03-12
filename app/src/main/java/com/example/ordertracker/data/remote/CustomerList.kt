package com.example.ordertracker.data.remote

import com.example.ordertracker.customers.CustomerModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CustomerList : CustomerApi {
    private val _customers = MutableStateFlow(
        listOf(
            CustomerModel(
                id = 1,
                customerName = "John Doe",
                contact = "0241234567",
                email = "john.doe@example.com",
                address = "123 Main St, Accra"
            ),
            CustomerModel(
                id = 2,
                customerName = "Jane Smith",
                contact = "0557654321",
                email = "jane.smith@example.com",
                address = "456 High St, Kumasi"
            ),
            CustomerModel(
                id = 3,
                customerName = "Robert Brown",
                contact = "0209876543",
                email = "robert.b@example.com",
                address = "789 Lake Rd, Takoradi"
            ),
            CustomerModel(
                id = 4,
                customerName = "Robert Brown",
                contact = "0209876543",
                email = "robert.b@example.com",
                address = "789 Lake Rd, Takoradi"
            ),
            CustomerModel(
                id = 5,
                customerName = "Robert Brown",
                contact = "0209876543",
                email = "robert.b@example.com",
                address = "789 Lake Rd, Takoradi"
            ),
            CustomerModel(
                id = 6,
                customerName = "Robert Brown",
                contact = "0209876543",
                email = "robert.b@example.com",
                address = "789 Lake Rd, Takoradi"
            ),
            CustomerModel(
                id = 7,
                customerName = "Robert Brown",
                contact = "0209876543",
                email = "robert.b@example.com",
                address = "789 Lake Rd, Takoradi"
            )
        )
    )
    override val customers: StateFlow<List<CustomerModel>> = _customers.asStateFlow()

    override suspend fun getCustomers(): List<CustomerModel> {
        return _customers.value
    }

    override suspend fun createCustomer(customer: CustomerModel) {
        _customers.value += customer
    }

    override suspend fun getCustomer(customerId: Long): CustomerModel? {
        return _customers.value.find { it.id == customerId }
    }

    override suspend fun deleteCustomer(customer: CustomerModel) {
        _customers.value -= customer
    }

    override suspend fun updateCustomer(customer: CustomerModel) {
        _customers.value = _customers.value.map {
            if (it.id == customer.id) customer else it
        }
    }
}