package model

import model.food.Food
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object OrderRepositoryImpl: OrderRepository {

    private val receipts = hashMapOf<Int, Receipt>()
    private var orderNum = 1
    private val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")


    override fun add(foods: Map<Food, Int>): String {
        val orderedTime = LocalDateTime.now()
        receipts[orderNum] = Receipt(
            orderNum++,
            orderedTime,
            foods,
        )
        return format.format(orderedTime)
    }
}