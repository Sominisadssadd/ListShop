package com.example.listshop.presentation

import android.app.Application
import androidx.lifecycle.*
import com.example.listshop.data.ShopListRepositoryImpl
import com.example.listshop.domain.ShopItem
import com.example.listshop.domain.UseCaseAddShopItem
import com.example.listshop.domain.UseCaseEditShopItem
import com.example.listshop.domain.UseCaseGetShopItem
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

class ShopItemActivityViewModel @Inject constructor(
    application: Application,
    val addShopItem: UseCaseAddShopItem,
    val editShopItem: UseCaseEditShopItem,
    val getShopItem: UseCaseGetShopItem
) : AndroidViewModel(application) {

    private val _errorName = MutableLiveData<Boolean>()
    val errorName: LiveData<Boolean>
        get() = _errorName

    private val _errorCount = MutableLiveData<Boolean>()
    val errorCount: LiveData<Boolean>
        get() = _errorCount

    private val _shouldClose = MutableLiveData<Unit>()
    val shouldClose: LiveData<Unit>
        get() = _shouldClose


    private val _shopItem = MutableLiveData<ShopItem>()
    val shopitem: LiveData<ShopItem>
        get() = _shopItem


    fun editShopItem(idOfOldShoptemElement: Int, name: String?, count: String?) {

        val pName = parseName(name)
        val pCount = parseCount(count)
        val valField = validateField(pName, pCount)
        if (valField) {
            shopitem.value?.let {
                viewModelScope.launch {
                    val newSI = it.copy(name = pName, count = pCount)
                    editShopItem.editShopItem(newSI)
                    _shouldClose.value = Unit
                }
            }
        }
    }

    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val shopItem = getShopItem.getShopItem(shopItemId)
            _shopItem.value = shopItem
        }
    }


    fun addShopItem(name: String?, count: String?) {
        val pName = parseName(name)
        val pCount = parseCount(count)
        val vFields = validateField(pName, pCount)
        if (vFields) {
            val shopItem = ShopItem(pName, pCount, true)
            viewModelScope.launch {
                addShopItem.addShopItem(shopItem)
                _shouldClose.value = Unit
            }
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