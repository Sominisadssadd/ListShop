package com.example.listshop.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.listshop.domain.ShopItem
import com.example.listshop.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = MutableLiveData<List<ShopItem>>()
    private val mutableShopList = sortedSetOf<ShopItem>({it1,it2-> it1.id.compareTo(it2.id)})



    init {
        for(i in 0 until 1000){
            val item = ShopItem("some stuff $i", i, Random.nextBoolean())
            addShopListItem(item)
        }
    }

    private var autoIncrementId = 0

    private fun updateShopList() {
        shopList.value = mutableShopList.toList()
    }

    override fun addShopListItem(shopItem: ShopItem) {

        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        mutableShopList.add(shopItem)
        updateShopList()
    }

    override fun editShopListItem(shopItem: ShopItem) {
        val oldShopItem = getShopListItem(shopItem.id)
        mutableShopList.remove(oldShopItem)
        addShopListItem(shopItem)
        updateShopList()
    }

    override fun removeShopListItem(shopItem: ShopItem) {
        mutableShopList.remove(shopItem)
        updateShopList()
    }

    override fun getShopListItem(id: Int): ShopItem {

        //можно сделать возвращаемый тип нулабельным, либо просто бросить исключение
        return mutableShopList.find { it.id == id }
            ?: throw RuntimeException("element with $id id not founded")

    }


    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopList
    }
}