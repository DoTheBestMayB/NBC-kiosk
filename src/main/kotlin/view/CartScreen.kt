package view

import model.Food
import model.ScreenCategory

class CartScreen: Screen() {

    override val category: ScreenCategory = ScreenCategory.CART

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