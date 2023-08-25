package com.example.listshop.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listshop.R
import com.example.listshop.domain.ShopItem


class ListShopRecyclerView : ListAdapter<ShopItem,ListShopRecyclerView.ListShopViewHolder>(ShopListDiffCallBack()) {

//    var listShop = listOf<ShopItem>()
//        set(value) {
//            //почему реализация с diffUtils не самая удачная? Потому, что метод
//            //calculateDiff выполняет все вычисления в главном потоке(Может тормозить)
//            //var diffCallBack = ShopListDiffCallBack(listShop, value)
//            //var calc = DiffUtil.calculateDiff(diffCallBack)
//            //calc.dispatchUpdatesTo(this)
//            field = value
//        }

    var ONShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var OnShopItemClikcListener: ((ShopItem) -> Unit)? = null
    var onShopItemSwipeAndDelete: ((ShopItem) -> Unit)? = null



    inner class ListShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.textViewName)
        val tvCount = view.findViewById<TextView>(R.id.textViewCount)
        val cardView = view.findViewById<CardView>(R.id.cardViewShopItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListShopViewHolder {
        var layoutId = when (viewType) {
            ABLE_ITEM -> R.layout.able_item
            DISABLE_ITEM -> R.layout.disable_item
            else -> throw RuntimeException("unknown ViewType $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(
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

        with(holder) {
            tvName.text = getItem(position).name
            tvCount.text = getItem(position).count.toString()
            cardView.setOnLongClickListener {
                ONShopItemLongClickListener?.invoke(getItem(position))
                true
            }
            cardView.setOnClickListener {
                OnShopItemClikcListener?.invoke(getItem(position))
            }
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