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

class ShopItemActivity : AppCompatActivity() {

    private lateinit var buttonSave: Button
    private lateinit var textIL1: TextInputLayout
    private lateinit var textIL2: TextInputLayout
    private lateinit var EDtext1: EditText
    private lateinit var EDtext2: EditText
    private var screenMode = UNDEFINED_SCREEN_MODE
    private var shopItemID = UNDEFINED_SHOP_ITEM_ID
    private lateinit var viewModel: ShopItemActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemActivityViewModel::class.java]
        initViews()
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()

        }
        observableEvents()

    }

    private fun launchEditMode() {
        val shopItem = viewModel.getShopItem(shopItemID)
        EDtext1.setText(shopItem.name)
        EDtext2.setText(shopItem.count.toString())
        buttonSave.setOnClickListener {
            viewModel.editShopItem(
                shopItemID,
                EDtext1.text.toString(),
                EDtext2.text.toString()
            )
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            val name = EDtext1.text.toString()
            val count = EDtext2.text.toString()
            viewModel.addShopItem(name, count)
        }
    }

    private fun observableEvents() {

        viewModel.errorName.observe(this) {
            if (it) {
                textIL1.error = getString(R.string.error_name_of_product_IL)
            }else{
                textIL1.error = null
            }

        }
        viewModel.errorCount.observe(this) {
            if (it) {
                textIL2.error = getString(R.string.error_name_of_count_IL)
            }else{
                textIL2.error = null
            }
        }
        viewModel.shouldClose.observe(this) {
            finish()
        }

        EDtext1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        EDtext2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetCount()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }

    private fun initViews() {
        buttonSave = findViewById(R.id.buttonSave)
        textIL1 = findViewById(R.id.text_il1)
        textIL2 = findViewById(R.id.text_il2)
        EDtext1 = findViewById(R.id.EDtext_1)
        EDtext2 = findViewById(R.id.EDtext_2)
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
            shopItemID = intent.getIntExtra(EXTRA_SHOP_ITEM_ARG, UNDEFINED_SHOP_ITEM_ID)
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
}