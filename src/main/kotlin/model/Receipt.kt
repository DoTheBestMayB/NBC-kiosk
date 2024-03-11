package model

import java.util.Date

data class Receipt(
    val orderNumber: Int,
    val orderTime: Date,
    val contents: List<Food>,
)
