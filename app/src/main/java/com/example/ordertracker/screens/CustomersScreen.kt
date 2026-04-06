package com.example.ordertracker.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.ordertracker.BottomNavItems
import com.example.ordertracker.MainScreensTopBar
import com.example.ordertracker.OrderTrackerBottomBar
import com.example.ordertracker.customers.CustomerHome
import com.example.ordertracker.navigation.Routes
import com.example.ordertracker.uistate.CustomersUiState
import com.example.ordertracker.viewmodels.CustomersViewModel
import com.example.ordertracker.viewmodels.SharedViewModel

@Composable
fun CustomersScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
    Scaffold(topBar = {
        MainScreensTopBar(
            title = "Customers"
        )
    }, bottomBar = {
        OrderTrackerBottomBar(navController = navController)
    }) { innerPadding ->

        Customer(
            modifier = Modifier.padding(innerPadding), sharedViewModel = sharedViewModel,
//            onCustomerClick = {
//                navController.popBackStack()
//            },
            onSearchClick = {
                navController.navigate(BottomNavItems.Search.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }, onNewCustomerClick = {
                navController.navigate(Routes.NEW_CUSTOMER)
            })
    }
}

@Composable
fun Customer(
    modifier: Modifier = Modifier,
    viewModel: CustomersViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,

    onSearchClick: () -> Unit,
    onNewCustomerClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is CustomersUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is CustomersUiState.Success -> {
                CustomerHome(
                    onSearchClick = { onSearchClick() },
                    customers = (uiState as CustomersUiState.Success).customers,
                    onNewCustomerClick = onNewCustomerClick,
                    sharedViewModel = sharedViewModel,
//                    onCustomerSelected = {
////                        onCustomerClick()
//                    }
                )
            }

            is CustomersUiState.Error -> {
                Text(
                    text = (uiState as CustomersUiState.Error).message,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                CustomerHome(
                    customers = emptyList(),
                    onSearchClick = onSearchClick,
                    onNewCustomerClick = onNewCustomerClick,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}
