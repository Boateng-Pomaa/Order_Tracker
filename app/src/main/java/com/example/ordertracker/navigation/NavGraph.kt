package com.example.ordertracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ordertracker.screens.CreateOrder
import com.example.ordertracker.screens.OrderDetailsScreen
import com.example.ordertracker.screens.OrderTrackerScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.ORDERS) {
        composable(Routes.ORDERS) {
            OrderTrackerScreen(
                onOrderClick = { orderId ->
                    navController.navigate(
                        Routes.ORDER_DETAILS.replace(
                            "{orderId}", orderId.toString()
                        )
                    )
                })
        }

        composable(
            route = Routes.ORDER_DETAILS, arguments = listOf(
                navArgument("orderId") { type = NavType.LongType })
        ) {
            OrderDetailsScreen(
                onBackClick = { navController.popBackStack() })
        }

    }

}

object Routes {
    const val ORDERS = "orders"
    const val CREATE_ORDER = "create_order"

    const val ORDER_DETAILS = "order_details/{orderId}"

    const val MAIN_SCREEN = "main_screen"


}