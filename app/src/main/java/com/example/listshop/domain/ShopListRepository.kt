package com.example.listshop.domain

interface ShopListRepository {

    fun addShopListItem(shopItem: ShopItem)

    fun editShopListItem(shopItem: ShopItem)

    fun removeShopListItem(shopItem: ShopItem)

    fun getShopListItem(id: Int): ShopItem

    fun getShopList(): List<ShopItem>
}