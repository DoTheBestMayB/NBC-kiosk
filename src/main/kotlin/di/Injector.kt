package di

import model.CardRepository
import model.CardRepositoryImpl
import model.MenuRepository
import model.MenuRepositoryImpl

object Injector {

    fun provideCardRepository(): CardRepository {
        return CardRepositoryImpl
    }

    fun provideMenuRepository(): MenuRepository {
        return MenuRepositoryImpl
    }
}