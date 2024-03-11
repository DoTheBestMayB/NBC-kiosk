# 요구사항 분석

- [ ] 프로그램을 실행하면 대분류의 메뉴판 번호들을 보여준다.
- [ ] 대분류 메뉴에 해당하는 숫자를 입력하면 세부 메뉴들을 보여준다.
- [ ] 대부류 메뉴에서 0번이 입력되면 프로그램을 종료한다.
- [ ] 하나의 리스트 객체로 모든 메뉴들을 관리한다.
- [ ] 메뉴판에 있는 숫자 외 값을 입력하면 다시 입력할 수 있도록 예외처리한다.
- [ ] Lv?? - 숫자가 아닌 콘솔 창의 화살표를 이용해 주문을 할 수 있도록 구현한다.
- [ ] 장바구니에 담긴 것이 있을 때만 ORDER MENU 선택지를 보여주고 허용한다.
---

## 아키텍처

- MVP 패턴을 적용한다.

## Model

---

### data

---
- Food(sealed interface)
    - `name: String`
    - `price: Int`
    - `description: String`
- Burger(data class : Food)
- Frozen Custard(data class : Food)
- Drinks(data class: Food)
- Beer(data class: Food)
- Receipt(data class)
  - `orderNumber: Int`
  - `orderTime: Date`
  - `contents: List<Food>`
- Step(enum class)
  - TOP_LEVEL_CATEGORY, SPECIFIC_CATEGORY, CONFIRM


### class Basket

---
- `getContents(): List<Menu>`

### class Card

---
- `cash: Int`
- `systemMaintenanceTime: Date`
- `getCash(): Int`
- `pay(amount: Int): Int`


## View

---

### class IO

---
- `showMenu(foods: List<Food>)`
 
## Presenter

---

### class Kiosk

---
- `loadMenu()`
- ``

## Contract

---

### interface KioskContract