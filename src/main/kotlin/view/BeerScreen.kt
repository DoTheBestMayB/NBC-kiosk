package view

import model.Food
import model.ScreenCategory

class BeerScreen: Screen() {

    override val category: ScreenCategory = ScreenCategory.BEER

    override fun showMenu(foods: List<Food>) {
        super.showMenu(foods)
        TODO("Not yet implemented")
    }

    override fun backToPreviousScreen() {
        TODO("Not yet implemented")
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return System.identityHashCode(this)
    }
}