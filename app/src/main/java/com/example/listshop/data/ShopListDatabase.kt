package com.example.listshop.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class ShopListDatabase : RoomDatabase() {


    //функция абстрактная потому, что у неё нет body, она только что-то возвращает
    abstract fun getShopListDao(): ShopListDao

    companion object {

        private const val DB_NAME = "shopItemDB.db"
        private var instance: ShopListDatabase? = null
        private val MONITOR = Any()

        fun getInstance(application: Application): ShopListDatabase {

            synchronized(MONITOR) {
                instance?.let {
                    return it
                }


                val db = Room.databaseBuilder(
                    application,
                    ShopListDatabase::class.java,
                    DB_NAME
                )
                    .build()

                instance = db
                return db
            }
        }
    }
}