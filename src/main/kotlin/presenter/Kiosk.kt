package presenter

import contract.KioskContract
import model.*
import model.food.*

class Kiosk(
    private val view: KioskContract.View,
) : KioskContract.Presenter {

    private val menu: HashMap<ScreenCategory, List<Food>> = hashMapOf(
        ScreenCategory.HOME to listOf(
            OverView("Burgers", "앵거스 비프 통살을 다져만든 버거", ScreenCategory.BURGERS),
            OverView("Forzen Custard", "매장에서 신선하게 만드는 아이스크림", ScreenCategory.FROZEN_CUSTARD),
            OverView("Drinks", "매장에서 직접 만드는 음료", ScreenCategory.DRINK),
            OverView("Beer", "뉴욕 브루클린 브루어리에서 양조한 맥주", ScreenCategory.BEER),
        ),
        ScreenCategory.BEER to listOf(
            Beer("Lager Light", 4500, "청량감이 뛰어난 라이트 라거, 부드러운 목넘김"),
            Beer("IPA Classic", 5500, "강렬한 홉의 향과 쓴맛이 특징인 클래식 IPA"),
            Beer("Stout Dark", 6000, "진하고 깊은 맛의 스타우트, 커피와 초콜릿 힌트"),
            Beer("Wheat Ale", 5000, "밝고 상쾌한 밀 맥주, 과일과 향신료의 미묘한 조화"),
        ),
        ScreenCategory.BURGERS to listOf(
            Burger("ShackBurger", 6900, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"),
            Burger("SmokeShack", 8900, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"),
            Burger("Shroom Burger", 9400, "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거"),
            Burger("Cheeseburger", 6900, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"),
            Burger("Hamburger", 5400, "비프패티를 기반으로 야채가 들어간 기본버거"),
        ),
        ScreenCategory.FROZEN_CUSTARD to listOf(
            FrozenCustard("Vanilla Classic", 3500, "매장에서 신선하게 만드는 부드러운 바닐라 커스터드"),
            FrozenCustard("Chocolate Bliss", 3800, "진하고 부드러운 초콜릿 커스터드, 매장에서 신선 제조"),
            FrozenCustard("Strawberry Swirl", 4000, "신선한 딸기와 바닐라 커스터드가 어우러진 환상의 맛"),
            FrozenCustard("Mint Chocolate Chip", 4200, "상쾌한 민트 커스터드에 초콜릿 칩이 콕콕, 매장에서 만듬"),
        ),
        ScreenCategory.DRINK to listOf(
            Drink("Iced Tea Classic", 2500, "청량감 가득한 클래식 아이스티, 매장에서 직접 우려내어 제공"),
            Drink("Lemonade Sparkle", 3000, "상큼한 레몬과 탄산의 조화, 매장에서 신선하게 만든 스파클링 레모네이드"),
            Drink("Coffee Brew", 2800, "선택된 원두로 직접 내린 신선한 커피, 매장에서 매일 제공"),
            Drink("Chocolate Shake", 3500, "진한 초콜릿과 우유의 완벽한 믹스, 매장에서 신선하게 만든 쉐이크"),
        ),
    )

    private val orderOptions = listOf(
        OverView("Order", "장바구니를 확인 후 주문합니다.", ScreenCategory.HOME),
        OverView("Cancel", "진행중인 주문을 취소합니다.", ScreenCategory.HOME),
    )

    private var loadedCategory: ScreenCategory? = null
    private var isAskingToAddToCart: Boolean = false
    private var pendingCartAdditionItem: Food? = null

    override fun loadMenu(category: ScreenCategory) {
        val foods = menu[category] ?: emptyList()
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
                    return
                }
                2 -> run {
                    val item = pendingCartAdditionItem ?: throw IllegalStateException(CRITICAL_ERROR)
                    isAskingToAddToCart = false
                    pendingCartAdditionItem = null
                    view.alertCartAddition(item, false)
                    return
                }
                else -> run {
                    view.showOutput(WRONG_INPUT)
                    return
                }
            }
        }

        menu[loadedCategory]?.let {
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
        if (Cart.cartItems.isEmpty()) {
            return
        }
        val startIndex = menu[ScreenCategory.HOME]?.size?.plus(1) ?: 1

        view.showOrderMenuOption(startIndex, orderOptions)
    }

    companion object {
        private const val NOT_ALLOWED_REQUEST = "올바르지 않은 요청입니다."
        private const val WRONG_INPUT = "잘못된 번호를 입력했어요 다시 입력해주세요."
        private const val CRITICAL_ERROR = "로직이 잘못되었습니다. 관리자에게 문의해주세요."
    }
}