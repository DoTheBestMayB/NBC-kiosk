package model.food

data class Drink(
    override val name: String,
    override val price: Int,
    override val description: String
) : Food
