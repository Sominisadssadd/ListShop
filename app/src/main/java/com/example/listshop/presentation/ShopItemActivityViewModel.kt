package com.example.listshop.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listshop.data.ShopListRepositoryImpl
import com.example.listshop.domain.ShopItem
import com.example.listshop.domain.UseCaseAddShopItem
import com.example.listshop.domain.UseCaseEditShopItem
import com.example.listshop.domain.UseCaseGetShopItem
import java.lang.Exception

class ShopItemActivityViewModel : ViewModel() {

    val repository = ShopListRepositoryImpl

    val addShopItem = UseCaseAddShopItem(repository)
    val editShopItem = UseCaseEditShopItem(repository)
    val getShopItem = UseCaseGetShopItem(repository)


    private val _errorName = MutableLiveData<Boolean>()
    val errorName: LiveData<Boolean>
        get() = _errorName

    private val _errorCount = MutableLiveData<Boolean>()
    val errorCount: LiveData<Boolean>
        get() = _errorCount

    private val _shouldClose = MutableLiveData<Unit>()
    val shouldClose: LiveData<Unit>
        get() = _shouldClose

    fun editShopItem(idOfOldShoptemElement: Int, name: String?, count: String?) {

        val pName = parseName(name)
        val pCount = parseCount(count)
        val valField = validateField(pName, pCount)
        if (valField) {
            val newSI = getShopItem(idOfOldShoptemElement).copy(name = pName, count = pCount)
            editShopItem.editShopItem(newSI)
            _shouldClose.value = Unit
        }
    }

    fun getShopItem(shopItemId: Int): ShopItem {
        return getShopItem.getShopItem(shopItemId)
    }


    fun addShopItem(name: String?, count: String?) {
        val pName = parseName(name)
        val pCount = parseCount(count)
        val vFields = validateField(pName, pCount)
        if (vFields) {
            val shopItem = ShopItem(pName, pCount, true)
            addShopItem.addShopItem(shopItem)
            _shouldClose.value = Unit
        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateField(name: String, count: Int): Boolean {
        var result = true
        if (name.isEmpty()) {
            _errorName.value = true
            result = false
        }
        if (count <= 0) {
            _errorCount.value = true
            result = false
        }
        return result
    }

    fun resetName() {
        _errorName.value = false
    }

    fun resetCount() {
        _errorCount.value = false
    }


}