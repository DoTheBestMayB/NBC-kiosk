package model

import model.food.Food

object Cart {
    private val cartItems: LinkedHashMap<Food, Int> = linkedMapOf()

    fun addItem(food: Food) {
        cartItems[food] = cartItems.getOrDefault(food, 0) + 1
    }
}