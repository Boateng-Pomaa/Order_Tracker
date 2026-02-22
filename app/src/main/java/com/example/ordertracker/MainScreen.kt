package com.example.ordertracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
    var selectedItem by remember { mutableStateOf<BottomNavItems>(BottomNavItems.Dashboard) }

    fun onBottomNavItemTap(item: BottomNavItems) {
        selectedItem = item
        navController.navigate(item.route)
    }


    NavHost(
        navController = navController,
        startDestination = BottomNavItems.Dashboard.route,
    ) {

        composable(BottomNavItems.Dashboard.route) {
            OrderTrackerAppHost(
                title = "Dashboard",
                floatingActionButton = {
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
                },
                onBottomNavItemTap = { onBottomNavItemTap(it) },
                selectedItem = selectedItem,
            ) { innerPadding ->
                OrderTrackerScreen(
                    modifier = Modifier.padding(innerPadding),
                    onOrderClick = { orderId ->
                        navController.navigate(
                            ORDER_DETAILS_ROUTE.replace(
                                "{orderId}", orderId.toString()
                            )
                        )
                    },
                )
            }
        }

        composable(
            route = ORDER_DETAILS_ROUTE,
            arguments = listOf(navArgument("orderId") { type = NavType.LongType })
        ) {
            OrderTrackerAppHost(
                title = "Order Details",
                navigationIcon = { OrderTrackerBackButton { navController.popBackStack() } },
                onBottomNavItemTap = { onBottomNavItemTap(it) },
                selectedItem = selectedItem,
            ) { innerPadding ->
                OrderDetailsScreen(
                    modifier = Modifier.padding(innerPadding),
                    onBackClick = { navController.popBackStack() },
                )
            }
        }

        composable(CREATE_ORDER_ROUTE) {
            OrderTrackerAppHost(
                title = "New Order",
                navigationIcon = { OrderTrackerBackButton { navController.popBackStack() } },
                onBottomNavItemTap = { onBottomNavItemTap(it) },
                selectedItem = selectedItem,
            ) { innerPadding ->
                CreateOrder(
                    modifier = Modifier.padding(innerPadding),
                    onOrderCreated = { navController.popBackStack() },
                )
            }

        }

        composable(BottomNavItems.Customers.route) {
            OrderTrackerAppHost(
                title = "Customers",
                onBottomNavItemTap = { onBottomNavItemTap(it) },
                selectedItem = BottomNavItems.Customers,
            ) { innerPadding ->
                CustomersScreen(modifier = Modifier.padding(innerPadding))
            }
        }

        composable(BottomNavItems.Search.route) {
            OrderTrackerAppHost(
                title = "Search",
                onBottomNavItemTap = { onBottomNavItemTap(it) },
                selectedItem = BottomNavItems.Search,
            ) { innerPadding ->
                SearchScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}


@Composable
fun OrderTrackerAppHost(
    title: String,
    selectedItem: BottomNavItems = BottomNavItems.Dashboard,
    onBottomNavItemTap: ((BottomNavItems) -> Unit)? = null,
    navigationIcon: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable ((PaddingValues) -> Unit),
) {
    Scaffold(
        topBar = { OrderTrackerTopAppBar(title = title, navigationIcon = navigationIcon) },
        bottomBar = {
            OrderTrackerBottomNavBar(
                selectedItem = selectedItem, onTap = onBottomNavItemTap
            )
        },
        content = content,
        floatingActionButton = floatingActionButton
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackerTopAppBar(title: String, navigationIcon: @Composable () -> Unit) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.secondary,
            navigationIconContentColor = MaterialTheme.colorScheme.secondary
        ),
        navigationIcon = navigationIcon,
        title = {
            Text(
                text = title, style = MaterialTheme.typography.titleLarge
            )
        },
    )
}

@Composable
fun OrderTrackerBackButton(onTap: (() -> Unit)? = null) {
    IconButton(onClick = { onTap?.invoke() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
        )
    }
}

@Composable
fun OrderTrackerBottomNavBar(
    selectedItem: BottomNavItems = BottomNavItems.Dashboard,
    onTap: ((BottomNavItems) -> Unit)? = null
) {
    val items = listOf(
        BottomNavItems.Dashboard, BottomNavItems.Customers, BottomNavItems.Search
    )

    BottomAppBar(
        contentPadding = PaddingValues(0.dp),
        containerColor = MaterialTheme.colorScheme.background,
        ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Black.copy(alpha = 0.1f))
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                items.forEach { item ->
                    val isSelected = selectedItem == item

                    val iconColor =
                        if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray

                    val textColor =
                        if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .width(109.dp)
                            .clickable { onTap?.invoke(item) }) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            tint = iconColor,
                            modifier = Modifier.size(18.dp)
                        )

                        Text(
                            text = item.title, fontSize = 11.sp, color = textColor
                        )
                    }
                }
            }
        }
    }
}