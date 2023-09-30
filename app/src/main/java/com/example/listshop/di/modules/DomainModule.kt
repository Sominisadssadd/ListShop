package com.example.listshop.di.modules

import com.example.listshop.data.ShopListRepositoryImpl
import com.example.listshop.domain.*
import dagger.Module
import dagger.Provides


@Module
class DomainModule {

    @Provides
    fun provideUseCaseAdd(repository: ShopListRepository) = UseCaseAddShopItem(repository)

    @Provides
    fun provideUseCaseEditShopItem(repository: ShopListRepository) =  UseCaseEditShopItem(repository)

    @Provides
    fun provideUseCaseGetShopItem(repository: ShopListRepository) = UseCaseGetShopItem(repository)

    @Provides
    fun provideUseCaseGetShopItemList(repository: ShopListRepository) = UseCaseGetShopItemList(repository)

    @Provides
    fun provideUseCaseRemoveShopItem(repository: ShopListRepository) = UseCaseRemoveShopItem(repository)

}