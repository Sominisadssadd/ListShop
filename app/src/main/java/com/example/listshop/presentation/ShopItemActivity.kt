package com.example.listshop.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.listshop.R
import com.example.listshop.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnFinishedListener {


    private var screenMode = UNDEFINED_SCREEN_MODE
    private var shopItemID = UNDEFINED_SHOP_ITEM_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()

        val fragment = when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newFragmentEdit(shopItemID)
            MODE_ADD -> ShopItemFragment.newFragmentAdd()
            else -> throw RuntimeException("Params screen mode is absent")
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }
    }

    private fun parseIntent() {


        if (!intent.hasExtra(EXTRA_SHOP_ACTIVITY_MODE_ARGS)) {
            throw RuntimeException("Params is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SHOP_ACTIVITY_MODE_ARGS)

        if ((mode != MODE_ADD) && (mode != MODE_EDIT)) {
            throw RuntimeException("Unknown screen mode ")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ARG)) {
                throw RuntimeException("ShopItem is absent")
            }
            shopItemID = intent.getIntExtra(
                EXTRA_SHOP_ITEM_ARG,
                UNDEFINED_SHOP_ITEM_ID
            )
        }
    }


    companion object {
        private const val UNDEFINED_SCREEN_MODE = ""
        private const val UNDEFINED_SHOP_ITEM_ID = -1
        private const val EXTRA_SHOP_ACTIVITY_MODE_ARGS = "args"
        private const val EXTRA_SHOP_ITEM_ARG = "shopItem"
        private const val MODE_ADD = "add"
        private const val MODE_EDIT = "edit"

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SHOP_ACTIVITY_MODE_ARGS, MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, shopItemID: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SHOP_ACTIVITY_MODE_ARGS, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ARG, shopItemID)
            return intent
        }
    }

    override fun finishedListener() {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        finish()
    }
}