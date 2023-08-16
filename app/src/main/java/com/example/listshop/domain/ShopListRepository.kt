package com.example.listshop.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addShopListItem(shopItem: ShopItem)

    fun editShopListItem(shopItem: ShopItem)

    fun removeShopListItem(shopItem: ShopItem)

    fun getShopListItem(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>
}