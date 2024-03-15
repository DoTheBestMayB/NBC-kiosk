package model

import model.food.Food

interface OrderRepository {

    fun add(foods: Map<Food, Int>): String
}