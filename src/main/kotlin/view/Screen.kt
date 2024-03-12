package view

import contract.KioskContract
import model.Food
import model.ScreenCategory

sealed class Screen: KioskContract.View {

    abstract val category: ScreenCategory

    override fun showMenu(foods: List<Food>) {
        ScreenStack.push(this)

        showTitle(category)

        val trailingSpace = foods.maxOf { it.name.length } + 2

        for ((index, food) in foods.withIndex()) {
            println("${index+1}. ${food.name.padEnd(trailingSpace)}|${convertPrice(food)}${food.description}")
        }
        println()
    }

    open fun backToPreviousScreen() {
        ScreenStack.pop()
    }

    private fun showTitle(category: ScreenCategory) {
        println("[ ${category.title} ]")
    }

    private fun convertPrice(food: Food): String {
        if (food.price == Food.EMPTY_PRICE) {
            return ""
        }
        return " W ${food.price/1000.0} |"
    }
}