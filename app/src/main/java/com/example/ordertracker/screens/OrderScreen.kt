package com.example.ordertracker.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ordertracker.orders.Home
import com.example.ordertracker.uistate.OrderUiState
import com.example.ordertracker.viewmodels.OrdersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackerScreen(
    viewModel: OrdersViewModel = hiltViewModel(), onOrderClick: (Long) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val orderToDelete by viewModel.orderToDelete.collectAsState()

    if (orderToDelete != null) {
        AlertDialog(
            onDismissRequest = { viewModel.cancelDelete() },
            title = { Text("Delete Order") },
            text = { Text("Are you sure you want to delete this order?") },
            confirmButton = {
                TextButton(onClick = { viewModel.confirmDelete() }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.cancelDelete() }) {
                    Text("Cancel")
                }
            })
    }

    Column(
        modifier = Modifier.fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
    ) {
        when (uiState) {
            is OrderUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is OrderUiState.Success -> {
                Home(
                    orders = (uiState as OrderUiState.Success).orders,
                    onDeleteOrderClick = { order ->
                        viewModel.requestDelete(order)
                    },
                    onOrderClick = { orderId ->
                        onOrderClick(orderId)
                    })
            }

            is OrderUiState.Error -> Text("Error")
        }
    }
}

