package com.example.ordertracker.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.ordertracker.OrderTrackerBottomBar
import com.example.ordertracker.OrderTrackerTopBar

@Composable
fun CustomersScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            OrderTrackerTopBar(
                title = "Customers"
            )
        }, bottomBar = {
            OrderTrackerBottomBar(navController = navController)
        }
    ) { innerPadding ->

        Customer(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun Customer(modifier: Modifier = Modifier) {

}