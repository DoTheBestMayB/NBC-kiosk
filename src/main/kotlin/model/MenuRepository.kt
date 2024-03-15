package model

import model.food.Food
import model.food.OverView

interface MenuRepository {

    fun getMenu(category: ScreenCategory?): List<Food>?

    fun getOrderOptions(): Map<Int, OverView>
}