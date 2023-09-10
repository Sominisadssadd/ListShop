package com.example.listshop.data

import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.listshop.domain.ShopItem
import com.example.listshop.domain.ShopListRepository
import kotlin.random.Random

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {


    private val shopListDB = ShopListDatabase.getInstance(application)
    private val shopListDao = shopListDB.getShopListDao()
    private val mapper = ShopListMapper()

    override suspend fun addShopListItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.shopItemToShopItemDB(shopItem))
    }

    override suspend fun editShopListItem(shopItem: ShopItem) {
        //Мы доабавляем в редактировании потому что у нас в Insert указанно
        //onConflict = replace
        shopListDao.addShopItem(mapper.shopItemToShopItemDB(shopItem))
    }

    override suspend fun removeShopListItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun getShopListItem(id: Int): ShopItem {

        //можно сделать возвращаемый тип нулабельным, либо просто бросить исключение
        val element = shopListDao.getShopItem(id)
        return mapper.shopItemDBtoShopItem(element)

    }


    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply{
        addSource(shopListDao.getListShopItems()){
            value = mapper.shopEntityListToShopItemList(it)
        }
    }


}