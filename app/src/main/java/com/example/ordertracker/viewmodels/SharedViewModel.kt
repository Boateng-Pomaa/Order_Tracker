package com.example.ordertracker.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ordertracker.customers.CustomerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    var selectedCustomer = mutableStateOf<CustomerModel?>(null)

    fun selectedCustomer(customer: CustomerModel) {
        selectedCustomer.value = customer
    }

    fun clearSelectedCustomer() {
        selectedCustomer.value = null
    }

}