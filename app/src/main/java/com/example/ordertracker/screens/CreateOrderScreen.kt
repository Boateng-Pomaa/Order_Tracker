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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ordertracker.BottomNavItems
import com.example.ordertracker.OrderTrackerTopBar
import com.example.ordertracker.R
import com.example.ordertracker.customers.ChooseFromCustomer
import com.example.ordertracker.customers.InputTextField
import com.example.ordertracker.orders.DeliverySelector
import com.example.ordertracker.orders.SectionHeader
import com.example.ordertracker.orders.StatusDropdown
import com.example.ordertracker.uistate.CreateOrderUiState
import com.example.ordertracker.viewmodels.CreateOrderViewModel
import com.example.ordertracker.viewmodels.SharedViewModel

@Composable
fun CreateOrderScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
    Scaffold(
        topBar = {
            OrderTrackerTopBar(
                title = "New Order",
                showBackButton = true,
                onBackClick = { navController.navigateUp() })
        }) { innerPadding ->
        CreateOrder(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            onOrderCreated = { navController.popBackStack() },
            sharedViewModel = sharedViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrder(
    modifier: Modifier = Modifier,
    onOrderCreated: () -> Unit,
    navController: NavHostController,
    viewModel: CreateOrderViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val selectedCustomer by sharedViewModel.selectedCustomer

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearForm()
            sharedViewModel.clearSelectedCustomer()
        }
    }

    LaunchedEffect(selectedCustomer) {
        selectedCustomer?.let {
            viewModel.onCustomerNameChange(0, it.customerName)
            viewModel.onContactChange(0, it.contact)
        }
    }

    if (state.showSuccessDialog) {
        Dialog(onDismissRequest = { viewModel.onDismissSuccessDialog() }) {
            Box(
                modifier = Modifier
                    .width(304.dp)
                    .height(252.dp)
                    .clip(RoundedCornerShape(20.dp))

                    .paint(
                        painter = painterResource(id = R.drawable.dialog_svg),
                        contentScale = ContentScale.FillBounds
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user_saved_svg),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Order created",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = 24.sp,
                        lineHeight = 26.4.sp,
                        letterSpacing = (-0.76).sp,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                    Spacer(modifier = Modifier.height(10.39.dp))

                    Text(
                        text = "The order was created successfully.",
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        fontSize = 15.sp,
                        lineHeight = 22.5.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Left
                    )

                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .height(44.dp)
                                .width(120.dp)
                                .clip(RoundedCornerShape(14.dp))

                        ) {
                            Image(
                                painter = painterResource(R.drawable.new_buttons_svg),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.matchParentSize()
                            )
                            Button(
                                onClick = {
                                    viewModel.onDismissSuccessDialog()
                                    sharedViewModel.clearSelectedCustomer()
                                    onOrderCreated()
                                },
                                modifier = Modifier.fillMaxSize(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                )
                            ) {

                                Text("Continue", color = MaterialTheme.colorScheme.onTertiary)
                            }
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .blur(if (state.showSuccessDialog) 5.dp else 0.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .imePadding()
                .verticalScroll(scrollState)
        ) {
            state.orders.forEachIndexed { index, orderState ->
                OrderForm(
                    state = orderState,
                    index = index,
                    viewModel = viewModel,
                    navController = navController,
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            Spacer(modifier = Modifier.height(180.dp))
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(bottom = 16.dp)
        ) {

            Button(
                onClick = { viewModel.createOrder() },
                enabled = state.orders[0].isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                ),
                modifier = Modifier.padding(horizontal = 20.dp),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()

                ) {
                    Image(
                        painter = painterResource(R.drawable.text_fields_background),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.matchParentSize()
                    )
                    Text(
                        text = "Save Order",
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Composable
fun OrderForm(
    state: CreateOrderUiState,
    index: Int,
    viewModel: CreateOrderViewModel,
    navController: NavHostController,
) {

    SectionHeader("CUSTOMER DETAILS", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    ChooseFromCustomer {
        navController.navigate(BottomNavItems.Search.route + "?isSelection=true")
    }
    Spacer(modifier = Modifier.height(16.dp))


    InputTextField(
        value = state.customerName,
        onValueChange = { viewModel.onCustomerNameChange(index, it) },
        label = "Full Name",
        error = state.customerNameError,
        readOnly = true,
        enabled = false
    )

    Spacer(modifier = Modifier.height(16.dp))

    InputTextField(
        value = state.contact,
        onValueChange = { viewModel.onContactChange(index, it) },
        label = "Contact Number",
        error = state.contactError,
        readOnly = true,
        enabled = false
    )

    Spacer(modifier = Modifier.height(32.dp))


    SectionHeader("ORDER DETAILS", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    InputTextField(
        value = state.item,
        onValueChange = { viewModel.onItemChange(index, it) },
        minLines = 4,
        label = "Item Description (e.g., Velvet Sofa Set)",
        error = state.itemError
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(

        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(modifier = Modifier.weight(1f)) {
            InputTextField(
                value = state.price,
                onValueChange = { viewModel.onPriceChange(index, it) },
                label = "Price (GHC)",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                error = state.priceError
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            InputTextField(
                value = state.units,
                onValueChange = { viewModel.onUnitsChange(index, it) },
                label = "Units",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                error = state.unitsError
            )
        }

    }

    Spacer(modifier = Modifier.height(32.dp))

    SectionHeader("DELIVERY OPTIONS", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    DeliverySelector(
        selected = state.delivery, onSelected = { viewModel.onDeliveryChange(index, it) })

    Spacer(modifier = Modifier.height(32.dp))
    SectionHeader("DELIVERY STATUS", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    StatusDropdown(
        selected = state.status, onSelected = { viewModel.onStatusChange(index, it) })
}
