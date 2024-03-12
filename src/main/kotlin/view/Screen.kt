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
        showExitOption(trailingSpace)
        println()
    }

    fun waitInput() {
        val input = readlnOrNull() ?: run {
            backToPreviousScreen()
            return
        }
        presenter.checkInput(input)
    }

    open fun backToPreviousScreen() {
        ScreenStack.pop()
    }

    private fun showExitOption(trailingSpace: Int) {
        if (category == ScreenCategory.HOME) {
            println("0. ${"종료".padEnd(trailingSpace)}| 프로그램 종료")
            return
        }
        println("0. ${"뒤로가기".padEnd(trailingSpace)}| 뒤로가기")
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