package com.example.listshop.domain

class UseCaseEditShopItem(private val shopListRepository: ShopListRepository) {

    suspend fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopListItem(shopItem)
    }
}