package com.example.ordertracker.orders

enum class Delivery(val label:String) {
    DELIVERY("Delivery"),
    SELF_PICKUP("Self Pickup")

}

enum class Status(val label:String) {
    DELIVERED("Delivered"),
    PICKED("Picked"),
    PENDING("Pending")
}