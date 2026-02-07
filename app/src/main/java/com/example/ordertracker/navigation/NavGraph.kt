package com.example.ordertracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ordertracker.orders.CreateOrder
import com.example.ordertracker.orders.OrderTrackerScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.ORDERS) {
        composable(Routes.ORDERS) {
            OrderTrackerScreen(
                onAddOrderClick = { navController.navigate(Routes.CREATE_ORDER) })
        }
        composable(Routes.CREATE_ORDER) {
            CreateOrder(
                onBackClick = { navController.popBackStack() },
                onOrderCreated = { navController.popBackStack() })
        }
    }

}

object Routes {
    const val ORDERS = "orders"
    const val CREATE_ORDER = "create_order"
}