package di

import model.*

object Injector {

    fun provideCardRepository(): CardRepository {
        return CardRepositoryImpl
    }

    fun provideMenuRepository(): MenuRepository {
        return MenuRepositoryImpl
    }

    fun provideOrderRepository(): OrderRepository {
        return OrderRepositoryImpl
    }
}