package com.example.listshop.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listshop.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), ShopItemFragment.OnFinishedListener {


    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recAdapter: ListShopRecyclerView
    private lateinit var buttonAdd: FloatingActionButton
    private var fragmentContainerView: FragmentContainerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAdd = findViewById(R.id.addShopItemButton)
        fragmentContainerView = findViewById(R.id.fragmentContainerL)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupRecyclerView()


        viewModel.getShopList().observe(this) {
            recAdapter.submitList(it)
        }


        buttonAdd.setOnClickListener {
            if (fragmentContainerView != null) {
                launchShopItemFragment(ShopItemFragment.newFragmentAdd())
            } else {
                val intentADD = ShopItemActivity.newIntentAdd(this@MainActivity)
                startActivity(intentADD)

            }
        }
    }


    private fun launchShopItemFragment(shopItemFragment: ShopItemFragment) {
        //popBackStack удаляет последний фрагмент из стека
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            //если мы будем использовать add и переворачивать экран, то фаргменты просто будут добавляться в контейнер
            .replace(R.id.fragmentContainerL, shopItemFragment)
            .addToBackStack(null)
            .commit()
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
            OnShopItemClikcListener = { it ->
                if (fragmentContainerView != null) {
                    launchShopItemFragment(ShopItemFragment.newFragmentEdit(it.id))
                } else {
                    val intentED = ShopItemActivity.newIntentEdit(this@MainActivity, it.id)
                    startActivity(intentED)
                }
            }
            onShopItemSwipeAndDelete = {
                viewModel.removeShopItem(it)
            }
        }
    }

    override fun finishedListener() {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        supportFragmentManager.popBackStack()
    }


}