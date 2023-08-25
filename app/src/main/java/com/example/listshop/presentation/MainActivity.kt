package com.example.listshop.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listshop.R
            

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recAdapter: ListShopRecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        viewModel.getShopList().observe(this) {
            recAdapter.submitList(it)
        }

    }

    private fun setupRecyclerView() {
        recAdapter = ListShopRecyclerView()
        recyclerView = findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val swipeToDeleteCallBack = SwipeToDeleteCallBack(recAdapter)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        with(recyclerView) {
            itemTouchHelper.attachToRecyclerView(this)
            adapter = recAdapter
            //увеличиваем размер пула viewHolder-ов для каждого viewType
            recycledViewPool.setMaxRecycledViews(
                ListShopRecyclerView.DISABLE_ITEM,
                10
            )
            recycledViewPool.setMaxRecycledViews(
                ListShopRecyclerView.ABLE_ITEM,
                10
            )

        }
        RecyclerViewEvents()
    }

    private fun RecyclerViewEvents() {
        with(recAdapter) {
            ONShopItemLongClickListener = {
                viewModel.changeEnabledState(it)
            }
            OnShopItemClikcListener = {
                Log.d(TAG, it.toString())
            }
            onShopItemSwipeAndDelete = {
                viewModel.removeShopItem(it)
            }
        }
    }

    companion object {
        private const val TAG = "MAINACTIVITY"
    }
}