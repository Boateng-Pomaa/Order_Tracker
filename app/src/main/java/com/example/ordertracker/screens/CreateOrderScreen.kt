package com.example.ordertracker.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavHostController
import com.example.ordertracker.OrderTrackerTopBar
import com.example.ordertracker.R
import com.example.ordertracker.orders.AppTextField
import com.example.ordertracker.orders.DeliverySelector
import com.example.ordertracker.orders.SectionHeader
import com.example.ordertracker.orders.StatusDropdown
import com.example.ordertracker.uistate.CreateOrderUiState
import com.example.ordertracker.viewmodels.CreateOrderViewModel



@Composable
fun CreateOrderScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            OrderTrackerTopBar(
                title = "New Order",
                showBackButton = true,
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        CreateOrder(
            modifier = Modifier.padding(innerPadding),
            onOrderCreated = { navController.popBackStack() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrder(modifier: Modifier = Modifier,
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
                        onOrderCreated()
                    }) {
                    Text("OK", color = MaterialTheme.colorScheme.secondary)
                }
            })
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)

        ) {
            state.orders.forEachIndexed { index, orderState ->

                OrderForm(
                    state = orderState, index = index, viewModel = viewModel
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
            // Add spacer at the bottom to avoid overlapping with bottom buttons if needed
            Spacer(modifier = Modifier.height(130.dp))
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Button(
                    onClick = { viewModel.addOrder() },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(2.dp),
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
                    contentPadding = PaddingValues(2.dp),
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
                onClick = { viewModel.createOrder() },
                enabled = state.orders[0].isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                ),
                modifier = Modifier.padding(horizontal = 16.dp),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(12.dp)
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
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun OrderForm(
    state: CreateOrderUiState, index: Int, viewModel: CreateOrderViewModel
) {

    SectionHeader("CUSTOMER DETAILS", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    AppTextField(
        value = state.customerName,
        onValueChange = { viewModel.onCustomerNameChange(index, it) },
        label = "Full Name",
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        singleLine = true,
        error = state.customerNameError
    )

    AppTextField(
        value = state.contact,
        onValueChange = { viewModel.onContactChange(index, it) },
        label = "Contact Number",
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        singleLine = true,
        error = state.contactError
    )

    SectionHeader("ORDER DETAILS", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    AppTextField(
        value = state.item,
        onValueChange = { viewModel.onItemChange(index, it) },
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        singleLine = false,
        minLines = 4,
        label = "Item Description"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppTextField(
            value = state.price,
            onValueChange = { viewModel.onPriceChange(index, it) },
            label = "Price (GHC)",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 5.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
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
            singleLine = true,
            error = state.unitsError
        )
    }

    SectionHeader("DELIVERY OPTIONS", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    DeliverySelector(
        selected = state.delivery, onSelected = { viewModel.onDeliveryChange(index, it) })

    SectionHeader("DELIVERY STATUS", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    StatusDropdown(
        selected = state.status, onSelected = { viewModel.onStatusChange(index, it) })
}
