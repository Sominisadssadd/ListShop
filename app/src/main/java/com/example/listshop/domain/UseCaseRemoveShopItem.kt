package com.example.listshop.domain

class UseCaseRemoveShopItem(private val shopListRepository: ShopListRepository) {

    suspend fun removeShopItem(shopItem: ShopItem){
        shopListRepository.removeShopListItem(shopItem)
    }
}