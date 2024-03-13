package model

import model.food.Food
import java.time.LocalDateTime

data class Receipt(
    val orderNumber: Int,
    val orderTime: LocalDateTime,
    val contents: Map<Food, Int>,
)
