package com.example.ordertracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ordertracker.navigation.Routes
import com.example.ordertracker.screens.CreateOrder
import com.example.ordertracker.screens.CustomersScreen
import com.example.ordertracker.screens.OrderDetailsScreen
import com.example.ordertracker.screens.OrderTrackerScreen
import com.example.ordertracker.screens.SearchScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Centralized screen configuration logic
    val currentScreen = remember(currentRoute) {
        Screen.fromRoute(currentRoute)
    }

    Scaffold(topBar = {
        OrderTrackerTopBar(
            title = currentScreen.title,
            showBackButton = currentScreen.showBackButton,
            onBackClick = { navController.navigateUp() })
    }, floatingActionButton = {
        if (currentScreen.showFAB) {
            OrderTrackerFAB(onClick = { navController.navigate(Routes.CREATE_ORDER) })
        }
    }, bottomBar = {
        if (currentScreen.showBottomBar) {
            OrderTrackerBottomBar(
                currentRoute = currentRoute, onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(BottomNavItems.Dashboard.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItems.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItems.Dashboard.route) {
                OrderTrackerScreen(onOrderClick = { orderId ->
                    navController.navigate(
                        Routes.ORDER_DETAILS.replace("{orderId}", orderId.toString())
                    )
                })
            }

            composable(
                route = Routes.ORDER_DETAILS,
                arguments = listOf(navArgument("orderId") { type = NavType.LongType })
            ) {
                OrderDetailsScreen(onBackClick = { navController.popBackStack() })
            }

            composable(Routes.CREATE_ORDER) {
                CreateOrder(onOrderCreated = { navController.popBackStack() })
            }

            composable(BottomNavItems.Customers.route) {
                CustomersScreen()
            }

            composable(BottomNavItems.Search.route) {
                SearchScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackerTopBar(
    title: String, showBackButton: Boolean, onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = if (showBackButton) 48.dp else 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title, style = MaterialTheme.typography.titleLarge, fontSize = 24.sp
            )
        }
    }, navigationIcon = {
        if (showBackButton) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.secondary,
        navigationIconContentColor = MaterialTheme.colorScheme.secondary
    )
    )
}

@Composable
fun OrderTrackerBottomBar(
    currentRoute: String?, onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItems.Dashboard, BottomNavItems.Customers, BottomNavItems.Search
    )
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                val color =
                    if (isSelected) MaterialTheme.colorScheme.onSurfaceVariant else Color.Gray

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .clickable { onNavigate(item.route) }
                        .padding(top = 4.dp)) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = color
                    )
                    Text(
                        text = item.title, fontSize = 10.sp, color = color
                    )
                }
            }
        }
    }
}

@Composable
fun OrderTrackerFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick, containerColor = Color.Transparent
    ) {
        Icon(
            painter = painterResource(R.drawable.add_button),
            contentDescription = "Add Order",
            tint = Color.Unspecified
        )
    }
}

/**
 * Metadata representation for each screen to handle UI state consistently.
 */
sealed class Screen(
    val title: String,
    val showBottomBar: Boolean = false,
    val showBackButton: Boolean = false,
    val showFAB: Boolean = false
) {
    object Dashboard :
        Screen("Dashboard", showBottomBar = true, showFAB = true)

    object Customers : Screen("Customers", showBottomBar = true)
    object Search : Screen("Search", showBottomBar = true)
    object CreateOrder : Screen("New Order", showBackButton = true)
    object OrderDetails : Screen("Order Details", showBackButton = true)

    companion object {
        fun fromRoute(route: String?): Screen = when (route) {
            BottomNavItems.Dashboard.route -> Dashboard
            BottomNavItems.Customers.route -> Customers
            BottomNavItems.Search.route -> Search
            Routes.CREATE_ORDER -> CreateOrder
            Routes.ORDER_DETAILS -> OrderDetails
            else -> Dashboard // Default fallback
        }
    }
}