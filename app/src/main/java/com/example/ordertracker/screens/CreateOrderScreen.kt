package com.example.ordertracker.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ordertracker.R
import com.example.ordertracker.orders.AppTextField
import com.example.ordertracker.orders.DeliverySelector
import com.example.ordertracker.orders.SectionHeader
import com.example.ordertracker.orders.StatusDropdown
import com.example.ordertracker.uistate.CreateOrderUiState
import com.example.ordertracker.viewmodels.CreateOrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrder(
    onOrderCreated: () -> Unit, viewModel: CreateOrderViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()


    if (state.showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onDismissSuccessDialog() },
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.secondary,
            textContentColor = MaterialTheme.colorScheme.secondary,
            title = { Text("Order Created") },
            text = { Text("The order was created successfully.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onDismissSuccessDialog()
                        onOrderCreated() // navigate back
                    }) {
                    Text("OK", color = MaterialTheme.colorScheme.secondary)
                }
            })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
    ) {
        state.orders.forEachIndexed { index, orderState ->

            OrderForm(
                state = orderState, index = index, viewModel = viewModel
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Button(
                onClick = { viewModel.addOrder() },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = Color.Unspecified
                )
            ) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.additional_order_button),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.matchParentSize()
                    )
                }
            }

            Button(
                onClick = { viewModel.removeLastOrder() },
                enabled = state.orders.size > 1,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = Color.Unspecified
                )
            ) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.remove_additional_order_button),
                        contentDescription = null,
                        alpha = if (state.orders.size > 1) 1f else 0.5f,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { viewModel.createOrder() }, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            )
        ) {
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(R.drawable.save_button),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize()
                )
                Text(
                    text = "Save Order",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

    }
}


@Composable
fun OrderForm(
    state: CreateOrderUiState, index: Int, viewModel: CreateOrderViewModel
) {

    SectionHeader("Customer Details", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    AppTextField(
        value = state.customerName,
        onValueChange = { viewModel.onCustomerNameChange(index, it) },
        label = "Full Name",
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        error = state.customerNameError
    )

    AppTextField(
        value = state.contact,
        onValueChange = { viewModel.onContactChange(index, it) },
        label = "Contact Number",
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        error = state.contactError
    )

    SectionHeader("Order Details", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    AppTextField(
        value = state.item,
        onValueChange = { viewModel.onItemChange(index, it) },
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        label = "Item Description"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppTextField(
            value = state.price,
            onValueChange = { viewModel.onPriceChange(index, it) },
            label = "Price (Ghc)",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            error = state.priceError
        )

        AppTextField(
            value = state.units,
            onValueChange = { viewModel.onUnitsChange(index, it) },
            label = "Units",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 5.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            error = state.unitsError
        )
    }

    SectionHeader("Delivery Options", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    DeliverySelector(
        selected = state.delivery, onSelected = { viewModel.onDeliveryChange(index, it) })

    SectionHeader("Delivery Status", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    StatusDropdown(
        selected = state.status, onSelected = { viewModel.onStatusChange(index, it) })
}
