package view

import model.ScreenCategory

class CartScreen: Screen() {

    override val category: ScreenCategory = ScreenCategory.CART

    override fun loadMenu() {
        presenter.showCartState()
    }
}