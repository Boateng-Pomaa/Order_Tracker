package com.example.ordertracker.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.ordertracker.OrderTrackerBottomBar
import com.example.ordertracker.OrderTrackerTopBar

@Composable
fun SearchScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            OrderTrackerTopBar(
                title = "Search"
            )
        }, bottomBar = {
            OrderTrackerBottomBar(navController = navController)
        }
    ) { innerPadding ->

        Search(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun Search(modifier: Modifier = Modifier) {

}