package model.food

data class FrozenCustard(
    override val name: String,
    override val price: Int,
    override val description: String
) : Food