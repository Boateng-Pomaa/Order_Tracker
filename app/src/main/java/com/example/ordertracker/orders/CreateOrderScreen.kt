package com.example.ordertracker.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrder(
    onBackClick: () -> Unit,
    onOrderCreated: () -> Unit,
    viewModel: CreateOrderViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()


    if (state.showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onDismissSuccessDialog() },
            title = { Text("Order Created") },
            text = { Text("The order was created successfully.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onDismissSuccessDialog()
                        onOrderCreated() // navigate back
                    }) {
                    Text("OK")
                }
            })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "New Order", style = MaterialTheme.typography.titleLarge
                        )
                    }
                }, navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                    navigationIconContentColor = MaterialTheme.colorScheme.secondary
                )
            )
        },

        ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SectionHeader("Customer Details")

            AppTextField(
                value = state.customerName,
                onValueChange = viewModel::onCustomerNameChange,
                label = "Full Name",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                error = state.customerNameError
            )

            AppTextField(
                value = state.contact,
                onValueChange = viewModel::onContactChange,
                label = "Contact Number",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                error = state.contactError
            )
            SectionHeader("Order Details")

            AppTextField(
                value = state.item,
                onValueChange = viewModel::onItemChange,
                label = "Item Description",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                singleLine = false
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AppTextField(
                    value = state.price,
                    onValueChange = viewModel::onPriceChange,
                    label = "Price (Ghc)",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, end = 16.dp),
                    error = state.priceError
                )
                AppTextField(
                    value = state.units,
                    onValueChange = viewModel::onUnitsChange,
                    label = "Units",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, end = 16.dp),
                    error = state.unitsError
                )
            }

            SectionHeader("Delivery Options")

            DeliverySelector(
                selected = state.delivery, onSelected = viewModel::onDeliveryChange
            )

            SectionHeader("Delivery Status")

            StatusDropdown(
                selected = state.status, onSelected = viewModel::onStatusChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.createOrder() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(text = "Save Order", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

    }
}