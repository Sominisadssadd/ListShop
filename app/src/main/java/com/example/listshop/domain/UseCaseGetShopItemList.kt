package com.example.listshop.domain

import androidx.lifecycle.LiveData

class UseCaseGetShopItemList(private val shopListRepository: ShopListRepository) {

    fun getShopList(): LiveData<List<ShopItem>>{
        return shopListRepository.getShopList()
    }

}