package com.example.listshop.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.listshop.presentation.ShopListApplication
import javax.inject.Inject

class ShopListProvider : ContentProvider() {


    @Inject
    lateinit var shopListDao: ShopListDao


    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.listshop", "listshop/*", LIST_SHOP_CODE_GET)
        addURI("com.example.listshop", "listshop/add", LIST_SHOP_CODE_ADD)
        addURI("com.example.listshop", "listshop/remove", LIST_SHOP_CODE_REMOVE)
    }


    private val component by lazy {
        (context as ShopListApplication).component
    }

    override fun onCreate(): Boolean {

        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        val code = uriMatcher.match(uri)

        return when (code) {
            LIST_SHOP_CODE_GET -> {
                shopListDao.getListShopItemsCursor()
            }
            else -> null
        }

    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val code = uriMatcher.match(uri)
        when (code) {
            LIST_SHOP_CODE_ADD -> {
                if (values == null) return null

                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val count = values.getAsInteger("count")
                val enabled = values.getAsBoolean("enabled")

                val shopItem = ShopItemDbModel(id, name, count, enabled)

                shopListDao.addShopItemProvider(shopItem)

            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            LIST_SHOP_CODE_REMOVE -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                return shopListDao.deleteShopItemProvider(id)
            }
        }
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }


    companion object {
        private const val LIST_SHOP_CODE_GET = 1
        private const val LIST_SHOP_CODE_ADD = 2
        private const val LIST_SHOP_CODE_REMOVE = 3
    }
}