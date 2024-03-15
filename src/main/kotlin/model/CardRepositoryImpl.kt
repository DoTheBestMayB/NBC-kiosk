package model

import java.time.LocalDateTime
import kotlin.concurrent.Volatile
import kotlin.random.Random

object CardRepositoryImpl : CardRepository {

    @Volatile
    private var nextUserId: Int = 1

    private val systemMaintenanceTime: LocalDateTime = LocalDateTime.now().plusMinutes(1)
    private const val DURATION_OF_MAINTENANCE = 10L
    private const val USER_NO_EXIST = "존재하지 않는 사용자입니다."
    private const val LACK_OF_BALANCE = "잔액이 부족합니다."
    private const val SERVER_MAINTENANCE_MESSAGE = "현재 서버 점검 중입니다."

    // Int : User를 구분짓는 고유 번호
    // Card : User가 가지고 있는 카드
    private val cards = hashMapOf<Int, Card>(
        Buildconfig.USER_KEY_ID to createCard(Buildconfig.USER_KEY_ID),
    )

    // 멀티 쓰레딩 환경에서 중복된 유저 아이디가 발급되지 않도록 방지
    override fun signUp(): Int {
        return synchronized(this) {
            cards[nextUserId] = createCard(nextUserId)
            nextUserId++
        }
    }

    override fun getCard(userKeyId: Int): Result<Card> {
        return when (val card = cards[userKeyId]) {
            null -> Result.Error(USER_NO_EXIST)
            else -> Result.Success(card)
        }
    }

    override fun pay(userId: Int, price: Int): Result<Card> {
        val card = cards[userId] ?: throw IllegalArgumentException(USER_NO_EXIST)
        if (checkMaintenance()) {
            return Result.Error(SERVER_MAINTENANCE_MESSAGE)
        }
        return if (price > card.cash) {
            Result.Error(LACK_OF_BALANCE)
        } else {
            val updatedCard = card.copy(cash = card.cash - price)
            cards[userId] = updatedCard
            Result.Success(updatedCard)
        }
    }

    private fun createRandomCash(): Int {
        return Random(System.currentTimeMillis()).nextInt(5_000, 100_001)
    }

    private fun createCard(userId: Int): Card {
        return Card(userId, createRandomCash(), systemMaintenanceTime, DURATION_OF_MAINTENANCE)
    }

    private fun checkMaintenance(): Boolean {
        val currentTime = LocalDateTime.now()
        return currentTime >= systemMaintenanceTime && currentTime <= systemMaintenanceTime.plusMinutes(
            DURATION_OF_MAINTENANCE
        )
    }
}