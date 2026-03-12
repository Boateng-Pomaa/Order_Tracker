package com.example.ordertracker.uistate

import com.example.ordertracker.customers.CustomerModel

sealed interface CustomersUiState {
    object Loading : CustomersUiState
    data class Success(val customers: List<CustomerModel>) : CustomersUiState
    data class Error(val message: String) : CustomersUiState
    object Empty : CustomersUiState

}
