package com.example.ordertracker

sealed class BottomNavItems(val route: String, val icon: Int, val title: String) {
    object Dashboard : BottomNavItems("dashboard", R.drawable.img_dashboard, "Dashboard")
    object Customers : BottomNavItems("customers", R.drawable.img_people, "Customers")
    object Search : BottomNavItems("search", R.drawable.img_search, "Search")
}