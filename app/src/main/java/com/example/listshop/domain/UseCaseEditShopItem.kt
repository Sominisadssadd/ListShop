package com.example.listshop.domain

import javax.inject.Inject

class UseCaseEditShopItem @Inject constructor( val shopListRepository: ShopListRepository) {

    suspend fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopListItem(shopItem)
    }
}