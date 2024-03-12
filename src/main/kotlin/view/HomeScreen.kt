package view

import contract.KioskContract
import model.Food
import model.ScreenCategory
import presenter.Kiosk

class HomeScreen : Screen() {

    override val category: ScreenCategory = ScreenCategory.HOME

    private val presenter: KioskContract.Presenter = Kiosk(this)

    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return System.identityHashCode(this)
    }

    fun start() {
        showWelcomeMessage()
        presenter.loadMenu(category)
    }

    private fun showWelcomeMessage() {
        println("SHAKESHACK BURGER 에 오신걸 환영합니다.")
        println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.")
        println()
    }
}