package com.example.ordertracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.ordertracker.OrderTrackerBottomBar
import com.example.ordertracker.OrderTrackerFAB
import com.example.ordertracker.OrderTrackerTopBar
import com.example.ordertracker.navigation.Routes

@Composable
fun DashboardScreen(navController: NavHostController) {
    Scaffold(topBar = {
        OrderTrackerTopBar(title = "Dashboard")
    }, bottomBar = {
        OrderTrackerBottomBar(navController = navController)
    }, floatingActionButton = {
        OrderTrackerFAB(onClick = { navController.navigate(Routes.CREATE_ORDER) })
    }) { innerPadding ->
        // Your Screen Content here
        OrderTrackerScreen(
            modifier = Modifier.padding(innerPadding), onOrderClick = { orderId ->
                navController.navigate(
                    Routes.ORDER_DETAILS.replace("{orderId}", orderId.toString())
                )
            })

    }
}