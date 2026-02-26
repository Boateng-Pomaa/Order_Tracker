package com.example.ordertracker.util

object ValidationUtil {
    fun validateCustomerName(name: String): String? =
        if (name.isBlank()) "Full name is required" else null

    fun validateContact(contact: String): String? =
        if (contact.length < 10) "Enter a valid phone number" else null

    fun validatePrice(price: String): String? {
        val priceValue = price.toDoubleOrNull()
        return when {
            priceValue == null -> "Enter a valid price"
            priceValue <= 0 -> "Price must be greater than 0"
            else -> null
        }
    }

    fun validateUnits(units: String): String? {
        val unitsValue = units.toLongOrNull()
        return when {
            unitsValue == null -> "Enter a valid number"
            unitsValue <= 0 -> "Units must be greater than 0"
            else -> null
        }
    }
}
