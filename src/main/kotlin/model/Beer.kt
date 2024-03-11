package model

data class Beer(
    override val name: String,
    override val price: Int,
    override val description: String
) : Food
