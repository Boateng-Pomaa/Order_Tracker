package com.example.ordertracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ordertracker.MainScreensTopBar
import com.example.ordertracker.OrderTrackerBottomBar
import com.example.ordertracker.customers.SearchBar
import com.example.ordertracker.search.SearchTypeSelector
import com.example.ordertracker.util.SearchType

@Composable
fun SearchScreen(navController: NavHostController) {
    Scaffold(topBar = {
        MainScreensTopBar(
            title = "Search"
        )
    }, bottomBar = {
        OrderTrackerBottomBar(navController = navController)
    }) { innerPadding ->

        Search(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun Search(modifier: Modifier = Modifier) {
    var selectedType by remember { mutableStateOf(SearchType.CUSTOMER) }

    Column(modifier = modifier) {
        Text(
            text = "Find customers or orders instantly",
            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar {}
        Spacer(modifier = Modifier.height(16.dp))

        SearchTypeSelector(
            selectedTab = selectedType, onTabSelected = { selectedType = it })
    }

}
