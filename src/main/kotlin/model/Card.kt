package model

import java.util.*

class Card(
    private var cash: Int,
    private var systemMaintenanceTime: Date,
) {

    fun getCashValance(): Int {
        return cash
    }

    fun pay(amount: Int): Boolean {
        if (amount > cash) return false
        cash -= amount
        return true
    }
}