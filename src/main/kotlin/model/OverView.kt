package model

data class OverView(
    override val name: String,
    override val description: String,
    override val price: Int = Food.EMPTY_PRICE
) : Food
