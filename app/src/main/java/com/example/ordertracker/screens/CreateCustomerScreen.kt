package com.example.ordertracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ordertracker.OrderTrackerTopBar
import com.example.ordertracker.customers.ChooseFromContact
import com.example.ordertracker.customers.CustomerForm
import com.example.ordertracker.viewmodels.CreateCustomerViewModel

@Composable
fun CreateCustomer(navController: NavHostController) {
    Scaffold(topBar = {
        OrderTrackerTopBar(
            title = "New Customer",
            showBackButton = true,
            onBackClick = { navController.navigateUp() })
    }) { innerPadding ->
        CustomerCreated(
            modifier = Modifier.padding(innerPadding),
            onCustomerCreated = { navController.popBackStack() }
        )
    }
}

@Composable
fun CustomerCreated(
    modifier: Modifier = Modifier,
    onCustomerCreated: () -> Unit,
    viewModel: CreateCustomerViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp)
    ) {

        ChooseFromContact { name, phone ->
            viewModel.onCustomerNameChange(name)
            viewModel.onContactChange(phone)
        }

        Spacer(modifier = Modifier.height(36.dp))

        CustomerForm(
            onOrderCreated = onCustomerCreated,
            viewModel = viewModel
        )
    }
}
