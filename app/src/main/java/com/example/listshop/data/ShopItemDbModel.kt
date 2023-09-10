package com.example.listshop.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.listshop.domain.ShopItem

const val TABLE_NAME = "shopItemTable"

@Entity(TABLE_NAME)
data class ShopItemDbModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("count")
    val count: Int,

    @ColumnInfo("enabled")
    val enabled: Boolean,
)