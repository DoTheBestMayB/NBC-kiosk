package presenter

import contract.KioskContract
import model.*

class Kiosk(
    private val view: KioskContract.View,
) : KioskContract.Presenter {

    private val menu: HashMap<ScreenCategory, List<Food>> = hashMapOf(
        ScreenCategory.HOME to listOf(
            OverView("Burgers", "앵거스 비프 통살을 다져만든 버거"),
            OverView("Forzen Custard", "매장에서 신선하게 만드는 아이스크림"),
            OverView("Drinks", "매장에서 직접 만드는 음료"),
            OverView("Beer", "뉴욕 브루클린 브루어리에서 양조한 맥주"),
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

    override fun loadMenu(category: ScreenCategory) {
        val foods = menu[category] ?: emptyList()
        view.showMenu(foods)
    }
}