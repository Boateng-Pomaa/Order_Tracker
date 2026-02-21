package com.example.ordertracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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

private const val CREATE_ORDER_ROUTE = "create_order"
private const val ORDER_DETAILS_ROUTE = "order_details/{orderId}"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val topBarState = remember(currentRoute) {
        when (// Case 1: Order Details Screen
            currentRoute) {
            Routes.ORDER_DETAILS -> TopBarState(
                title = "Order Details", isBackButtonVisible = true
            )
            // Case 2: Create Order Screen
            Routes.CREATE_ORDER -> TopBarState(
                title = "New Order", isBackButtonVisible = true
            )
            // Case 3: Any screen in the main bottom bar flow
            in listOf(
                BottomNavItems.Dashboard.route,
                BottomNavItems.Customers.route,
                BottomNavItems.Search.route
            ) -> TopBarState(
                title = when (currentRoute) {
                    BottomNavItems.Customers.route -> "Customers"
                    BottomNavItems.Search.route -> "Search"
                    else -> "Dashboard"
                }, isBackButtonVisible = false
            )

            else -> TopBarState(isBackButtonVisible = false)
        }
    }

    val items = listOf(
        BottomNavItems.Dashboard, BottomNavItems.Customers, BottomNavItems.Search
    )

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = topBarState.title, style = MaterialTheme.typography.titleLarge
                    )
                }
            }, navigationIcon = {
                if (topBarState.isBackButtonVisible) {
                    IconButton(onClick = { navController.navigateUp() }) { // Back action
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
    }, floatingActionButton = {
        if (currentRoute == BottomNavItems.Dashboard.route) {
            FloatingActionButton(
                onClick = { navController.navigate(CREATE_ORDER_ROUTE) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Order",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }, bottomBar = {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1F),
            color = MaterialTheme.colorScheme.background,
            shadowElevation = 8.dp // Add some elevation for visual separation
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                items.forEach { item ->
                    val isSelected = currentRoute == item.route

                    val iconColor =
                        if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray

                    val textColor =
                        if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .clickable { navController.navigate(item.route) }
                            .padding(top = 4.dp)

                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            tint = iconColor
                        )

                        Text(
                            text = item.title, fontSize = 10.sp, color = textColor
                        )
                    }
                }
            }
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
                        ORDER_DETAILS_ROUTE.replace(
                            "{orderId}", orderId.toString()
                        )
                    )
                })
            }

            composable(
                route = ORDER_DETAILS_ROUTE,
                arguments = listOf(navArgument("orderId") { type = NavType.LongType })
            ) {
                OrderDetailsScreen(onBackClick = { navController.popBackStack() })
            }

            composable(CREATE_ORDER_ROUTE) {
                CreateOrder(
                    onOrderCreated = { navController.popBackStack() })
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

data class TopBarState(val title: String = "", val isBackButtonVisible: Boolean)