package com.example.listshop.domain

class UseCaseGetShopItemList(private val shopListRepository: ShopListRepository) {

    fun getShopList(): List<ShopItem>{
        return shopListRepository.getShopList()
    }

}