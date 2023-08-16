package com.example.listshop.domain

class UseCaseEditShopItem(private val shopListRepository: ShopListRepository) {

    fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopListItem(shopItem)
    }
}