package com.example.ordertracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordertracker.customers.CustomerModel
import com.example.ordertracker.data.repository.CustomerRepository
import com.example.ordertracker.uistate.CreateCustomerUiState
import com.example.ordertracker.util.ValidationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCustomerViewModel @Inject constructor(private val customerRepository: CustomerRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(CreateCustomerUiState())
    val uiState: StateFlow<CreateCustomerUiState> = _uiState

    fun onCustomerNameChange(value: String) {
        _uiState.update { current ->
            val error = ValidationUtil.validateCustomerName(value)

            current.copy(
                customerName = value, customerNameError = error
            )
        }
    }

    fun onContactChange(value: String) {
        _uiState.update { current ->
            val error = ValidationUtil.validateContact(value)

            current.copy(
                contact = value, contactError = error
            )

        }
    }

    fun onEmailChange(value: String) {
        _uiState.update { current ->
            val error = ValidationUtil.validateEmail(value)

            current.copy(
                email = value, emailError = error
            )

        }
    }

    fun onAddressChange(value: String) {
        _uiState.update { current ->
            val error = ValidationUtil.validateAddress(value)

            current.copy(
                address = value, addressError = error
            )

        }
    }

    fun onSaveCustomer() {
        val currentState = _uiState.value

        // Double check validation before saving
        if (currentState.isFormValid) {
            viewModelScope.launch {
                try {
                    val newCustomer = CustomerModel(
                        id = System.currentTimeMillis(),
                        customerName = currentState.customerName,
                        contact = currentState.contact,
                        email = currentState.email,
                        address = currentState.address
                    )

                    customerRepository.createCustomer(newCustomer)

                    // Trigger the success dialog (which you already handle in UI)
                    _uiState.update { it.copy(showSuccessDialog = true) }
                } catch (e: Exception) {
                    _uiState.update { it.copy(error = "Invalid input") }
                }
            }
        }
    }

//    fun loadCustomers() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val list = customerRepository.getCustomers()
//                _customers.value = list
//            } catch (e: Exception) {
//                // Handle error
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

    fun onDismissSuccessDialog() {
        _uiState.update {
            it.copy(showSuccessDialog = false)
        }
    }


}
