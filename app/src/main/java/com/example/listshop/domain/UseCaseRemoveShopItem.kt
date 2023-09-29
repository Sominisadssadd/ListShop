package com.example.listshop.domain

import javax.inject.Inject

class UseCaseRemoveShopItem @Inject constructor(val shopListRepository: ShopListRepository) {

    suspend fun removeShopItem(shopItem: ShopItem) {
        shopListRepository.removeShopListItem(shopItem)
    }
}