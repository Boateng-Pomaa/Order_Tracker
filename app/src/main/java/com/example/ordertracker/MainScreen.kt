package com.example.ordertracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ordertracker.navigation.Routes
import com.example.ordertracker.screens.CreateCustomer
import com.example.ordertracker.screens.CreateOrderScreen
import com.example.ordertracker.screens.CustomersScreen
import com.example.ordertracker.screens.DashboardScreen
import com.example.ordertracker.screens.DetailsScreen
import com.example.ordertracker.screens.SearchScreen
import com.example.ordertracker.viewmodels.SharedViewModel


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(
        navController = navController, startDestination = BottomNavItems.Dashboard.route
    ) {

        composable(BottomNavItems.Dashboard.route) {
            DashboardScreen(navController)
        }

        composable(Routes.CREATE_ORDER) {
            CreateOrderScreen(navController, sharedViewModel)
        }

        composable(
            route = Routes.ORDER_DETAILS,
            arguments = listOf(navArgument("orderId") { type = NavType.LongType })
        ) {
            DetailsScreen(navController)
        }

        composable(BottomNavItems.Customers.route) {
            CustomersScreen(navController, sharedViewModel)
        }

        composable(
            route = BottomNavItems.Search.route + "?isSelection={isSelection}",
            arguments = listOf(navArgument("isSelection") {
                type = NavType.BoolType
                defaultValue = false
            })
        ) { backStackEntry ->
            val isSelection = backStackEntry.arguments?.getBoolean("isSelection") ?: false
            SearchScreen(navController, sharedViewModel, isSelection)
        }

        composable(Routes.NEW_CUSTOMER) {
            CreateCustomer(navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackerTopBar(
    title: String, showBackButton: Boolean = false, onBackClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = if (showBackButton) 48.dp else 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onTertiary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.tertiary)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreensTopBar(
    title: String
) {
    TopAppBar(
        title = {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onTertiary
                )
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
    navController: NavHostController
) {
    val items = listOf(BottomNavItems.Dashboard, BottomNavItems.Customers, BottomNavItems.Search)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == item.route } == true
                val color =
                    if (isSelected) MaterialTheme.colorScheme.onSurfaceVariant else Color.Gray

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = color
                    )
                    Text(text = item.title, fontSize = 10.sp, color = color)
                }
            }
        }
    }
}


@Composable
fun OrderTrackerFAB(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.fab_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )

        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Order",
            tint = Color.Black,
            modifier = Modifier.size(28.dp)
        )
    }
}
