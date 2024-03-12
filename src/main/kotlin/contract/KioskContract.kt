package contract

import model.food.Food
import model.ScreenCategory

interface KioskContract {
    interface View {

        fun showMenu(foods: List<Food>)
        fun showOutput(sentence: String)
        fun exit()
        fun showConfirm(selectedOption: Food)
        fun moveTo(screenType: ScreenCategory)
        fun alertCartAddition(item: Food, isAdded: Boolean)
    }

    interface Presenter {

        fun loadMenu(category: ScreenCategory)
        fun checkInput(input: String)
    }
}