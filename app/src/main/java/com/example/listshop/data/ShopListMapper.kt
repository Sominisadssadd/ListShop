package com.example.listshop.data

import com.example.listshop.domain.ShopItem

class ShopListMapper {

    fun shopItemDBtoShopItem(shopItemDbModel: ShopItemDbModel): ShopItem {
        return with(shopItemDbModel) {
            ShopItem(
                id = id,
                name = name,
                count = count,
                enabled = enabled
            )
        }
    }

    fun shopItemToShopItemDB(shopItem: ShopItem): ShopItemDbModel {
        return with(shopItem) {
            ShopItemDbModel(
                id, name, count, enabled
            )
        }
    }

    fun shopEntityListToShopItemList(shopItemList: List<ShopItemDbModel>) = shopItemList.map {
        shopItemDBtoShopItem(it)
    }


}