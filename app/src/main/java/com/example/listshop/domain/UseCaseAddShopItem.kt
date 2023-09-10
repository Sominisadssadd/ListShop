package com.example.listshop.domain

class UseCaseAddShopItem(private  val shopListRepository: ShopListRepository) {

    suspend fun addShopItem(shopItem: ShopItem){
        shopListRepository.addShopListItem(shopItem)
    }
}