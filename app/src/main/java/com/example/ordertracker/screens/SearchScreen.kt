package com.example.ordertracker.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ordertracker.MainScreensTopBar
import com.example.ordertracker.OrderTrackerBottomBar
import com.example.ordertracker.OrderTrackerTopBar
import com.example.ordertracker.customers.CustomerItem
import com.example.ordertracker.customers.SearchBar
import com.example.ordertracker.search.OrderSearch
import com.example.ordertracker.search.SearchTypeSelector
import com.example.ordertracker.uistate.CustomersUiState
import com.example.ordertracker.uistate.OrderUiState
import com.example.ordertracker.util.SearchType
import com.example.ordertracker.viewmodels.CustomersViewModel
import com.example.ordertracker.viewmodels.OrdersViewModel
import com.example.ordertracker.viewmodels.SharedViewModel

@Composable
fun SearchScreen(
    navController: NavHostController, sharedViewModel: SharedViewModel, isSelection: Boolean = false
) {
    Scaffold(topBar = {
        if (isSelection) {
            OrderTrackerTopBar(
                title = "Select Customer",
                showBackButton = true,
                onBackClick = { navController.navigateUp() })
        } else {
            MainScreensTopBar(title = "Search")
        }
    }, bottomBar = {
        if (!isSelection) {
            OrderTrackerBottomBar(navController = navController)
        }
    }) { innerPadding ->

        Search(
            modifier = Modifier.padding(innerPadding),
            sharedViewModel = sharedViewModel,
            isSelection = isSelection,
            onCustomerSelected = {
                navController.popBackStack()
            },
            onOrderSelected = { orderId ->
                navController.navigate("order_details/$orderId")
            })
    }
}

@Composable
fun Search(
    modifier: Modifier = Modifier,
    customersViewModel: CustomersViewModel = hiltViewModel(),
    ordersViewModel: OrdersViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel(),
    isSelection: Boolean = false,
    onCustomerSelected: () -> Unit,
    onOrderSelected: (Long) -> Unit
) {
    val customersUiState by customersViewModel.uiState.collectAsState()
    val ordersUiState by ordersViewModel.uiState.collectAsState()
    var selectedType by remember { mutableStateOf(SearchType.CUSTOMER) }
    var searchQuery by remember { mutableStateOf("") }


    Column(modifier = modifier.padding(horizontal = 20.dp)) {

        Column {
            Text(
                text = "Find customers or orders instantly",
                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                enabled = true,
                value = searchQuery,
                onValueChange = { searchQuery = it })

            Spacer(modifier = Modifier.height(16.dp))

            SearchTypeSelector(
                enabled = !isSelection,
                selectedTab = selectedType,
                onTabSelected = { selectedType = it })
        }


        Spacer(modifier = Modifier.height(20.dp))

        if (selectedType == SearchType.CUSTOMER) {
            when (customersUiState) {
                is CustomersUiState.Success -> {
                    val customers = (customersUiState as CustomersUiState.Success).customers
                    val filteredCustomers = if (searchQuery.isEmpty()) {
                        customers
                    } else {
                        customers.filter {
                            it.customerName.contains(searchQuery, ignoreCase = true) ||
                                    it.contact.contains(searchQuery, ignoreCase = true) ||
                                    it.email.contains(searchQuery, ignoreCase = true)
                        }
                    }

                    if (filteredCustomers.isEmpty()) {
                        Text(text = "No customers found matching \"$searchQuery\"", color = MaterialTheme.colorScheme.secondary)
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(filteredCustomers, key = { it.id }) { customer ->
                                CustomerItem(
                                    customerModel = customer, onCustomerClick = {
                                        sharedViewModel.selectedCustomer(customer)
                                        onCustomerSelected()
                                    })
                            }
                        }
                    }
                }

                is CustomersUiState.Loading -> {
                    LoadingIndicator()
                }

                is CustomersUiState.Error -> {
                    Text(text = (customersUiState as CustomersUiState.Error).message)
                }

                is CustomersUiState.Empty -> {
                    Text(text = "No customers found")
                }
            }
        } else {
            when (ordersUiState) {
                is OrderUiState.Success -> {
                    val orders = (ordersUiState as OrderUiState.Success).orders
                    val filteredOrders = if (searchQuery.isEmpty()) {
                        orders
                    } else {
                        orders.filter {
                            it.customerName.contains(searchQuery, ignoreCase = true) ||
                                    it.item.contains(searchQuery, ignoreCase = true)
                        }
                    }

                    if (filteredOrders.isEmpty()) {
                        Text(text = "No orders found matching \"$searchQuery\"", color = MaterialTheme.colorScheme.secondary)
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(filteredOrders, key = { it.id }) { order ->
                                OrderSearch(order = order, onOrderClick = onOrderSelected)
                            }
                        }
                    }
                }

                is OrderUiState.Loading -> {
                    LoadingIndicator()
                }

                is OrderUiState.Error -> {
                    Text(text = (ordersUiState as OrderUiState.Error).message)
                }
            }
        }
    }

}

@Composable
fun LoadingIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}
