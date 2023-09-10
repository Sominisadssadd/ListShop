package com.example.listshop.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listshop.R
import com.example.listshop.databinding.AbleItemBinding
import com.example.listshop.databinding.DisableItemBinding
import com.example.listshop.domain.ShopItem


class ListShopRecyclerView :
    ListAdapter<ShopItem, ListShopRecyclerView.ListShopViewHolder>(ShopListDiffCallBack()) {


    var ONShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var OnShopItemClikcListener: ((ShopItem) -> Unit)? = null
    var onShopItemSwipeAndDelete: ((ShopItem) -> Unit)? = null


    inner class ListShopViewHolder(val binding: ViewDataBinding) :

        RecyclerView.ViewHolder(binding.root)

       

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListShopViewHolder {
        val layoutId = when (viewType) {
            ABLE_ITEM -> R.layout.able_item
            DISABLE_ITEM -> R.layout.disable_item
            else -> throw RuntimeException("unknown ViewType $viewType")
        }


     
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            layoutId,
            parent,
            false
        )
        return ListShopViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) {
            ABLE_ITEM
        } else {
            DISABLE_ITEM
        }
    }

    override fun onBindViewHolder(holder: ListShopViewHolder, position: Int) {

        val binding = holder.binding
        val item = getItem(position)
        with(binding) {

            root.setOnLongClickListener {
                ONShopItemLongClickListener?.invoke(getItem(position))
                true
            }
            root.setOnClickListener {
                OnShopItemClikcListener?.invoke(getItem(position))
            }
        }

        when (binding) {
            is AbleItemBinding -> binding.shopItem = item
            is DisableItemBinding -> binding.shopItem = item
            else -> throw RuntimeException("Unknown ViewDataBinding type")
        }

    }

    fun removeShopItemElements(shopItem: ShopItem) {
        onShopItemSwipeAndDelete?.invoke(shopItem)
    }


    companion object {
        const val ABLE_ITEM = 1
        const val DISABLE_ITEM = 0
        private const val TAG = "MAINACTIVITY"
    }
}