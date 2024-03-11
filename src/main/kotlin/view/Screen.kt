package view

import model.Food

sealed interface Screen {

    fun showMenu(foods: List<Food>)
    fun backToPreviousScreen()

}