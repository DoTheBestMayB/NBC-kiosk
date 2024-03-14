package contract

import model.Card
import model.ScreenCategory
import model.food.Food
import model.food.OverView

interface KioskContract {
    interface View {

        fun showMenu(foods: List<Food>)
        fun showOutput(sentence: String)
        fun exit()
        fun showConfirm(selectedOption: Food)
        fun moveTo(screenType: ScreenCategory)
        fun alertCartAddition(item: Food, isAdded: Boolean)
        fun showOrderMenuOption(startIndex: Int, options: List<OverView>)
        fun showCartItem(cartItems: Map<Food, Int>)
        fun showCardInfo(card: Card)
    }

    interface Presenter {

        fun loadMenu(category: ScreenCategory)
        fun checkInput(input: String)
        fun checkOrderMenuOptionVisibility()
        fun showCartState()
        fun loadCardInfo()
    }
}