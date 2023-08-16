package com.example.listshop.domain

class UseCaseGetShopItem(private val shopListRepository: ShopListRepository) {

    fun getShopItem(id: Int): ShopItem {
        return shopListRepository.getShopListItem(id)
    }

}