package com.example.listshop.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.listshop.domain.ShopItem


@Dao
interface ShopListDao {

    @Query("select * from shopItemTable")
     fun getListShopItems(): LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItem: ShopItemDbModel)

    @Query("delete from shopItemTable where id =:shopItemId")
    suspend fun deleteShopItem(shopItemId: Int)

    @Query("select * from shopItemTable where id =:shopItemId limit 1")
    suspend fun getShopItem(shopItemId: Int): ShopItemDbModel

}