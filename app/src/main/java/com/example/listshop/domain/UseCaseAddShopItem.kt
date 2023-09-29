package com.example.listshop.domain

import javax.inject.Inject

class UseCaseAddShopItem @Inject constructor(  val shopListRepository: ShopListRepository) {

    suspend fun addShopItem(shopItem: ShopItem){
        shopListRepository.addShopListItem(shopItem)
    }
}