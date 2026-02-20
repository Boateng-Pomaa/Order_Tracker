package com.example.ordertracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordertracker.data.repository.OrderRepository
import com.example.ordertracker.uistate.CreateOrdersScreenState
import com.example.ordertracker.orders.Delivery
import com.example.ordertracker.orders.OrderModel
import com.example.ordertracker.orders.Status
import com.example.ordertracker.uistate.CreateOrderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class CreateOrderViewModel @Inject constructor(private val orderRepository: OrderRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(CreateOrdersScreenState())
    val uiState: StateFlow<CreateOrdersScreenState> = _uiState

    fun onCustomerNameChange(index: Int, value: String) {
        _uiState.update { current ->
            if (index >= current.orders.size) return@update current
            val updatedOrders = current.orders.toMutableList()
            val order = updatedOrders[index]
            val error = validateCustomerName(value)
            updatedOrders[index] = order.copy(customerName = value, customerNameError = error)
            current.copy(orders = updatedOrders)
        }
    }

    fun onContactChange(index: Int, value: String) {
        _uiState.update { current ->
            if (index >= current.orders.size) return@update current
            val updatedOrders = current.orders.toMutableList()
            val order = updatedOrders[index]
            val error = validateContact(value)
            updatedOrders[index] = order.copy(contact = value, contactError = error)
            current.copy(orders = updatedOrders)
        }
    }

    fun onItemChange(index: Int, value: String) {
        _uiState.update { current ->
            if (index >= current.orders.size) return@update current
            val updatedOrders = current.orders.toMutableList()
            val order = updatedOrders[index]
            updatedOrders[index] = order.copy(item = value)
            current.copy(orders = updatedOrders)
        }
    }

    fun onUnitsChange(index: Int, value: String) {
        if (value.all { it.isDigit() }) {
            _uiState.update { current ->
                if (index >= current.orders.size) return@update current
                val updatedOrders = current.orders.toMutableList()
                val order = updatedOrders[index]
                updatedOrders[index] = order.copy(units = value)
                current.copy(orders = updatedOrders)
            }
        }
    }

    fun onPriceChange(index: Int, value: String) {
        if (value.matches(Regex("^\\d*\\.?\\d*$"))) {
            _uiState.update { current ->
                if (index >= current.orders.size) return@update current
                val updatedOrders = current.orders.toMutableList()
                val order = updatedOrders[index]
                val error = validatePrice(value)
                updatedOrders[index] = order.copy(price = value, priceError = error)
                current.copy(orders = updatedOrders)
            }
        }
    }

    fun onDeliveryChange(index: Int, delivery: Delivery) {
        _uiState.update { current ->
            if (index >= current.orders.size) return@update current
            val updatedOrders = current.orders.toMutableList()
            val order = updatedOrders[index]
            updatedOrders[index] = order.copy(delivery = delivery)
            current.copy(orders = updatedOrders)
        }
    }

    fun onStatusChange(index: Int, status: Status) {
        _uiState.update { current ->
            if (index >= current.orders.size) return@update current
            val updatedOrders = current.orders.toMutableList()
            val order = updatedOrders[index]
            updatedOrders[index] = order.copy(status = status)
            current.copy(orders = updatedOrders)
        }
    }


    fun createOrder() {
        viewModelScope.launch {
            try {
                val screenState = _uiState.value

                screenState.orders.forEachIndexed { index, orderState ->

                    val order = OrderModel(
                        id = System.currentTimeMillis() + index,
                        customerName = orderState.customerName,
                        item = orderState.item,
                        units = orderState.units.toLongOrNull() ?: 0L,
                        price = orderState.price.toDoubleOrNull() ?: 0.0,
                        status = orderState.status,
                        delivery = orderState.delivery,
                        contact = orderState.contact
                    )

                    orderRepository.createOrder(order)
                }

                _uiState.update { it.copy(showSuccessDialog = true) }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Invalid input")
                }
            }
        }
    }

    fun onDismissSuccessDialog() {
        _uiState.update {
            it.copy(showSuccessDialog = false)
        }
    }

    private fun validateCustomerName(name: String): String? =
        if (name.isBlank()) "Full name is required" else null

    private fun validateContact(contact: String): String? =
        if (contact.length < 10) "Enter a valid phone number" else null

    private fun validatePrice(price: String): String? {
        val priceValue = price.toDoubleOrNull()
        return when {
            priceValue == null -> "Enter a valid price"
            priceValue <= 0 -> "Price must be greater than 0"
            else -> null
        }
    }

    fun addOrder() {
        _uiState.update { current ->
            current.copy(
                orders = current.orders + CreateOrderUiState()
            )
        }
    }

    fun removeLastOrder() {
        _uiState.update { current ->
            if (current.orders.size > 1) {
                current.copy(orders = current.orders.dropLast(1))
            } else current
        }
    }
}