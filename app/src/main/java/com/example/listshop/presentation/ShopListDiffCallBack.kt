package com.example.listshop.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.listshop.domain.ShopItem

class ShopListDiffCallBack : DiffUtil.ItemCallback<ShopItem>() {

    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem) = oldItem == newItem


}