package com.example.listshop.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listshop.data.ShopListRepositoryImpl
import com.example.listshop.domain.ShopItem
import com.example.listshop.domain.UseCaseEditShopItem
import com.example.listshop.domain.UseCaseGetShopItemList
import com.example.listshop.domain.UseCaseRemoveShopItem

class MainViewModel : ViewModel() {


    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = UseCaseGetShopItemList(repository)
    private val editShopItemUseCase = UseCaseEditShopItem(repository)
    private val removeShopItemUseCase = UseCaseRemoveShopItem(repository)



    fun getShopList(): LiveData<List<ShopItem>> {
        return getShopListUseCase.getShopList()
    }


    fun changeEnabledState(shopItem: ShopItem) {
        var oldElement = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(shopItem)
    }

    fun removeShopItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
    }

}