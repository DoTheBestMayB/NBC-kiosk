package model

import model.food.Food

object Cart {
    private val _cartItems: LinkedHashMap<Food, Int> = linkedMapOf()
    val cartItems: Map<Food, Int>
        get() = _cartItems

    fun addItem(food: Food) {
        _cartItems[food] = _cartItems.getOrDefault(food, 0) + 1
    }

    fun clear() {
        _cartItems.clear()
    }
}