package model

interface CardRepository {

    fun signUp(): Int
    fun getCard(userKeyId: Int): Result<Card>
    fun pay(userId: Int, price: Int): Result<Card>
}