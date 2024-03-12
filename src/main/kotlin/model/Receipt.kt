package model

import model.food.Food
import java.util.Date

data class Receipt(
    val orderNumber: Int,
    val orderTime: Date,
    val contents: List<Food>,
)
