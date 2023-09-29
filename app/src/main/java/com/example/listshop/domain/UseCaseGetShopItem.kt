package com.example.listshop.domain

import javax.inject.Inject

class UseCaseGetShopItem @Inject constructor( val shopListRepository: ShopListRepository) {

    suspend fun getShopItem(id: Int): ShopItem {
        return shopListRepository.getShopListItem(id)
    }

}