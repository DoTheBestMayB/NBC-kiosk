package model

import java.time.LocalDateTime

data class Card(
    val userId: Int,
    val cash: Int,
    val systemMaintenanceTime: LocalDateTime,
    val duration: Long,
)