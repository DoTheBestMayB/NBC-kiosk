package view

import model.ScreenCategory

class HomeScreen : Screen() {

    override val category: ScreenCategory = ScreenCategory.HOME

    fun start() {
        showWelcomeMessage()
        loadMenu()
    }

    private fun showWelcomeMessage() {
        println("SHAKESHACK BURGER 에 오신걸 환영합니다.")
        println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.")
        println()
    }
}