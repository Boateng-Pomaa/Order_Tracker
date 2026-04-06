package com.example.ordertracker.uistate

sealed interface CustomersUiState {
    object Loading : CustomersUiState
    data class Success(val customers: List<CustomerUiModel>) : CustomersUiState
    data class Error(val message: String) : CustomersUiState
    object Empty : CustomersUiState
}
