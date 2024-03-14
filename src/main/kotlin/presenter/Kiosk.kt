package presenter

import contract.KioskContract
import model.*
import model.food.Food
import model.food.OverView

class Kiosk(
    private val view: KioskContract.View,
    private val cardRepository: CardRepository,
    private val menuRepository: MenuRepository,
) : KioskContract.Presenter {

    private var loadedCategory: ScreenCategory? = null
    private var isAskingToAddToCart: Boolean = false
    private var pendingCartAdditionItem: Food? = null

    override fun loadMenu(category: ScreenCategory) {
        val foods = menuRepository.getMenu(category) ?: run {
            view.showOutput(NOT_ALLOWED_REQUEST)
            return
        }
        loadedCategory = category

        view.showMenu(foods)
    }

    override fun checkInput(input: String) {
        if (loadedCategory == null) {
            view.showOutput(NOT_ALLOWED_REQUEST)
            return
        }
        val inputNum = input.toIntOrNull() ?: run {
            view.showOutput(WRONG_INPUT)
            return
        }
        if (inputNum == 0) {
            view.exit()
            return
        }

        if (isAskingToAddToCart) {
            when (inputNum) {
                1 -> run {
                    val item = pendingCartAdditionItem ?: throw IllegalStateException(CRITICAL_ERROR)
                    Cart.addItem(item)
                    isAskingToAddToCart = false
                    pendingCartAdditionItem = null
                    view.alertCartAddition(item, true)
                }
                2 -> run {
                    val item = pendingCartAdditionItem ?: throw IllegalStateException(CRITICAL_ERROR)
                    isAskingToAddToCart = false
                    pendingCartAdditionItem = null
                    view.alertCartAddition(item, false)
                }
                else -> run {
                    view.showOutput(WRONG_INPUT)
                }
            }
            return
        }

        // Home 화면에서 ORDER MENU 옵션을 입력했는지 검사한다.
        if (loadedCategory == ScreenCategory.HOME && checkCartItemExist()) {
            when(menuRepository.getOrderOptions()[inputNum]?.screenType) {
                ScreenCategory.CART -> {
                    view.moveTo(ScreenCategory.CART)
                    return
                }
                ScreenCategory.HOME -> run {
                    clearUserSelectInfo()
                    return
                }
                else -> Unit
            }
        } else if (loadedCategory == ScreenCategory.CART) {
            if (inputNum == 1) {
                val items = Cart.cartItems
                val totalPrice = items.map { it.key.price * it.value }.sum()

                // 결제 여부 확인
                when (val result = cardRepository.pay(Buildconfig.USER_KEY_ID, totalPrice)) {
                    is Result.Success -> {
                        val orderedTime = OrderManager.add(items)
                        Cart.clear()
                        view.showOutput("결제를 완료했습니다. (${orderedTime})\n\n")
                    }
                    is Result.Error -> {
                        view.showOutput(result.message)
                    }
                }
                view.exit()
            } else {
                view.showOutput(WRONG_INPUT)
            }
            return
        }


        menuRepository.getMenu(loadedCategory)?.let {
            val selectedOption = it.getOrNull(inputNum-1) ?: run {
                view.showOutput(WRONG_INPUT)
                return
            }
            if (loadedCategory == ScreenCategory.HOME) {
                view.moveTo((selectedOption as OverView).screenType)
            } else {
                isAskingToAddToCart = true
                pendingCartAdditionItem = selectedOption
                view.showConfirm(selectedOption)
            }
        } ?: view.showOutput(NOT_ALLOWED_REQUEST)
    }

    // Order Menu는 홈 화면에서만 보일 수 있기 때문에, 앞에 표시되는 index 숫자는 ScreenCategory.HOME 이후로 설정함
    override fun checkOrderMenuOptionVisibility() {
        if (checkCartItemExist().not()) {
            return
        }

        val startIndex = menuRepository.getMenu(ScreenCategory.HOME)?.size?.plus(1) ?: 1

        view.showOrderMenuOption(startIndex, menuRepository.getOrderOptions().values.toList())
    }

    override fun showCartState() {
        loadedCategory = ScreenCategory.CART
        view.showCartItem(Cart.cartItems)
    }

    override fun loadCardInfo() {
        when (val result = cardRepository.getCard(Buildconfig.USER_KEY_ID)) {
            is Result.Success -> view.showCardInfo(result.data)
            is Result.Error -> view.showOutput(result.message)
        }
    }

    private fun checkCartItemExist(): Boolean = Cart.cartItems.isNotEmpty()

    private fun clearUserSelectInfo() {
        Cart.clear()
        loadedCategory = null
        isAskingToAddToCart = false
        pendingCartAdditionItem = null
    }

    companion object {
        private const val NOT_ALLOWED_REQUEST = "올바르지 않은 요청입니다."
        private const val WRONG_INPUT = "잘못된 번호를 입력했어요 다시 입력해주세요."
        private const val CRITICAL_ERROR = "로직이 잘못되었습니다. 관리자에게 문의해주세요."
    }
}