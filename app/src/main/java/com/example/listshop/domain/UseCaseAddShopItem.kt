package com.example.listshop.domain

class UseCaseAddShopItem(private  val shopListRepository: ShopListRepository) {

    fun addShopItem(shopItem: ShopItem){
        shopListRepository.addShopListItem(shopItem)
    }
}