package model

data class OverView(
    override val name: String,
    override val description: String,
    val screenType: ScreenCategory,
    override val price: Int = Food.EMPTY_PRICE,
) : Food
