package view

import contract.KioskContract
import model.Food
import model.ScreenCategory
import presenter.Kiosk
import kotlin.math.max

sealed class Screen : KioskContract.View {

    abstract val category: ScreenCategory
    protected var presenter: KioskContract.Presenter = Kiosk(this)
    private var isConfirmCalled = false

    override fun showMenu(foods: List<Food>) {
        ScreenStack.push(this)

        showTitle(category)

        val trailingSpace = max(foods.maxOf { it.name.length } + 2, "뒤로가기".length + 2)

        for ((index, food) in foods.withIndex()) {
            println("${index + 1}. ${createSentence(food, trailingSpace)}")
        }
        showExitOption(trailingSpace)
        println()
    }

    override fun showConfirm(selectedOption: Food) {
        isConfirmCalled = true
        println(createSentence(selectedOption, selectedOption.name.length + 2))
        println(CONFORM_MESSAGE)
        println()
        println()
    }

    override fun showOutput(sentence: String) {
        println(sentence)
    }

    override fun exit() {
        backToPreviousScreen()
    }

    override fun moveTo(screenType: ScreenCategory) {
        val createdScreen = createScreen(screenType)
        ScreenStack.push(createdScreen)
    }

    fun waitInput() {
        val input = readlnOrNull() ?: run {
            backToPreviousScreen()
            return
        }
        presenter.checkInput(input)
    }

    fun loadMenu() {
        if (isConfirmCalled) { // 장바구니 담는 것의 확인 요청이 들어온 경우 넘어감
            isConfirmCalled = false
            return
        }
        presenter.loadMenu(category)
    }

    private fun createScreen(screenType: ScreenCategory): Screen {
        return when(screenType) {
            ScreenCategory.BEER -> BeerScreen()
            ScreenCategory.BURGERS -> BurgersScreen()
            ScreenCategory.DRINK -> DrinkScreen()
            ScreenCategory.FROZEN_CUSTARD -> FrozenCustardScreen()
            ScreenCategory.HOME -> HomeScreen()
            ScreenCategory.CART -> CartScreen()
        }
    }

    private fun backToPreviousScreen() {
        ScreenStack.pop()
    }

    private fun showExitOption(trailingSpace: Int) {
        if (category == ScreenCategory.HOME) {
            println("0. ${"종료".padEnd(trailingSpace-1)}| 프로그램 종료")
            return
        }
        println("0. ${"뒤로가기".padEnd(trailingSpace-2)}| 뒤로가기")
    }

    private fun showTitle(category: ScreenCategory) {
        println("[ ${category.title} ]")
    }

    private fun convertPrice(food: Food): String {
        if (food.price == Food.EMPTY_PRICE) {
            return ""
        }
        return " W ${food.price / 1000.0} |"
    }

    private fun createSentence(food: Food, trailingSpace: Int): String {
        return "${food.name.padEnd(trailingSpace)}| ${convertPrice(food)}${food.description}"
    }

    companion object {
        private const val CONFORM_MESSAGE = "위 메뉴를 장바구니에 추가하시겠습니까?\n1. 확인\t\t2.취소"
    }
}