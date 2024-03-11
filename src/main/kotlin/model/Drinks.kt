package model

data class Drinks(
    override val name: String,
    override val price: Int,
    override val description: String
) : Food
