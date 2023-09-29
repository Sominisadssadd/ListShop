package com.example.listshop.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class UseCaseGetShopItemList @Inject constructor(val shopListRepository: ShopListRepository) {

    fun getShopList(): LiveData<List<ShopItem>>{
        return shopListRepository.getShopList()
    }

}