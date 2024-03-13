package view

import contract.KioskContract
import model.food.Food
import model.ScreenCategory
import model.food.OverView
import presenter.Kiosk
import kotlin.math.max

sealed class Screen : KioskContract.View {

    abstract val category: ScreenCategory
    private var presenter: KioskContract.Presenter = Kiosk(this)
    private var isConfirmCalled = false

    override fun showMenu(foods: List<Food>) {
        ScreenStack.push(this)

        showTitle(category)
        showOption(1, foods, true)
    }

    override fun showConfirm(selectedOption: Food) {
        isConfirmCalled = true
        println(createSentence(selectedOption, selectedOption.name.length + 2))
        println(CONFORM_MESSAGE)
        println("\n")
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

    override fun alertCartAddition(item: Food, isAdded: Boolean) {
        if (isAdded) {
            println("\n${item.name} 가 장바구니에 추가되었습니다.\n")
        }
        isConfirmCalled = false
    }

    override fun showOrderMenuOption(startIndex: Int, options: List<OverView>) {
        showOrderOptionTitle()

        showOption(startIndex, options, false)
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
            return
        }
        presenter.loadMenu(category)
        if (category == ScreenCategory.HOME) {
            presenter.checkOrderMenuOptionVisibility()
        }
    }

    private fun showOption(startIndex: Int, options: List<Food>, isShowExitOption: Boolean) {
        val trailingSpace = max(options.maxOf { it.name.length } + 2, "뒤로가기".length + 2)

        for ((index, food) in options.withIndex()) {
            println("${startIndex + index}. ${createSentence(food, trailingSpace)}")
        }

        if (isShowExitOption) {
            showExitOption(trailingSpace)
        }
        println()
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

    private fun showOrderOptionTitle() {
        println("[  ORDER MENU ]")
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