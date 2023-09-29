package com.example.listshop.data

import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.listshop.domain.ShopItem
import com.example.listshop.domain.ShopListRepository
import javax.inject.Inject
import kotlin.random.Random

class ShopListRepositoryImpl @Inject constructor(
    application: Application
) : ShopListRepository {

    //Посмотреть
    private val shopListDB = ShopListDatabase.getInstance(application)
    private val shopListDao = shopListDB.getShopListDao()
    private val mapper = ShopListMapper()

    override suspend fun addShopListItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.shopItemToShopItemDB(shopItem))
    }

    override suspend fun editShopListItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.shopItemToShopItemDB(shopItem))
    }

    override suspend fun removeShopListItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun getShopListItem(id: Int): ShopItem {

        val element = shopListDao.getShopItem(id)
        return mapper.shopItemDBtoShopItem(element)

    }


    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply{
        addSource(shopListDao.getListShopItems()){
            value = mapper.shopEntityListToShopItemList(it)
        }
    }


}