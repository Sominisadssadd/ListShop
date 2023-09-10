package com.example.listshop.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    suspend fun addShopListItem(shopItem: ShopItem)

    suspend fun editShopListItem(shopItem: ShopItem)

    suspend fun removeShopListItem(shopItem: ShopItem)

    suspend fun getShopListItem(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>
}