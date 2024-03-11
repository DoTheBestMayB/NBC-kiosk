package model

data class Burger(
    override val name: String,
    override val price: Int,
    override val description: String
) : Food