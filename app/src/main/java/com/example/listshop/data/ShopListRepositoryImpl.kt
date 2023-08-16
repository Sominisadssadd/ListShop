package com.example.listshop.data

import com.example.listshop.domain.ShopItem
import com.example.listshop.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    override fun addShopListItem(shopItem: ShopItem) {

        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun editShopListItem(shopItem: ShopItem) {
        val oldShopItem = getShopListItem(shopItem.id)
        shopList.remove(oldShopItem)
        addShopListItem(shopItem)
    }

    override fun removeShopListItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun getShopListItem(id: Int): ShopItem {

        //можно сделать возвращаемый тип нулабельным, либо просто бросить исключение
        return shopList.find { it.id == id }
            ?: throw RuntimeException("element with $id id not founded")
    }


    override fun getShopList(): List<ShopItem> {
        //возвращаем копию листа, чтобы нельзя было работать с оригиналом
        return shopList.toList()
    }
}