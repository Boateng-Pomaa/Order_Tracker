package com.example.ordertracker.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.ordertracker.OrderTrackerTopBar

@Composable
fun CreateCustomer(navController: NavHostController) {
    Scaffold(topBar = {
        OrderTrackerTopBar(
            title = "New Customer",
            showBackButton = true,
            onBackClick = { navController.navigateUp() })
    }) { innerPadding ->
        CustomerCreated(modifier = Modifier.padding(innerPadding))


    }
}

@Composable
fun CustomerCreated(modifier: Modifier = Modifier) {

}
