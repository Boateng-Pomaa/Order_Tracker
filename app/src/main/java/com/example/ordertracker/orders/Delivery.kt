package com.example.ordertracker.orders

enum class Delivery(val label:String) {
    SELF_PICKUP("Self Pickup"),
    DELIVERY("Delivery")
}

enum class Status(val label:String) {
    DELIVERED("Delivered"),
    PICKED("Picked"),
    PENDING("Pending")
}