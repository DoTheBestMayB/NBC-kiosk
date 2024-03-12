package contract

import model.Food
import model.ScreenCategory

interface KioskContract {
    interface View {

        fun showMenu(foods: List<Food>)
    }

    interface Presenter {

        fun loadMenu(category: ScreenCategory)
    }
}