package view

import model.Food
import model.ScreenCategory

class DrinkScreen: Screen() {

    override val category: ScreenCategory = ScreenCategory.DRINK

    override fun showMenu(foods: List<Food>) {
        super.showMenu(foods)
        TODO("Not yet implemented")
    }

    override fun backToPreviousScreen() {
        super.backToPreviousScreen()
        TODO("Not yet implemented")
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return System.identityHashCode(this)
    }
}