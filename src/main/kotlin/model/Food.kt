package model

sealed interface Food {
    val name: String
    val price: Int
    val description: String
}