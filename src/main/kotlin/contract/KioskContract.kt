package contract

import model.Food

interface KioskContract {
    interface View {

        fun showMenu(foods: List<Food>)
    }

    interface Presenter {

        fun loadMenu()
    }
}