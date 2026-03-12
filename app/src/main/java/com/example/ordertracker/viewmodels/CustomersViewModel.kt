package com.example.ordertracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordertracker.data.repository.CustomerRepository
import com.example.ordertracker.uistate.CustomersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(private val customerRepository: CustomerRepository) :
    ViewModel() {


    val uiState: StateFlow<CustomersUiState> =
        customerRepository.customers.map { CustomersUiState.Success(it) as CustomersUiState }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CustomersUiState.Loading
            )




}
