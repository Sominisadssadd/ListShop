package com.example.listshop.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.listshop.R
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var buttonSave: Button
    private lateinit var textIL1: TextInputLayout
    private lateinit var textIL2: TextInputLayout
    private lateinit var EDtext1: EditText
    private lateinit var EDtext2: EditText
    private var screenMode = UNDEFINED_SCREEN_MODE
    private var shopItemID = UNDEFINED_SHOP_ITEM_ID
    private lateinit var viewModel: ShopItemActivityViewModel


    private lateinit var onShopItemFinishedListener: OnFinishedListener


    //onAttach вызывается в момент прикреления фрагмента к активити
    //context - это наша активити и зкоторой он был вызван
    //здесь проверяем реализует ли активити интерфейс,жизненно необходимый данному фрагменту
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFinishedListener) {
            onShopItemFinishedListener = context
        } else {
            throw RuntimeException("Activity should implements OnFinishedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //let выполнится только в том случае, если обьект от которого вызвана функция не null
        //что позволяет избежать проверки на null

        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemActivityViewModel::class.java]
        initViews(view)
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

        viewModel.errorName.observe(viewLifecycleOwner) {
            if (it) {
                textIL1.error = getString(R.string.error_name_of_product_IL)
            } else {
                textIL1.error = null
            }

        }
        viewModel.errorCount.observe(viewLifecycleOwner) {
            if (it) {
                textIL2.error = getString(R.string.error_name_of_count_IL)
            } else {
                textIL2.error = null
            }
        }
        viewModel.shouldClose.observe(viewLifecycleOwner) {
            onShopItemFinishedListener.finishedListener()
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

    interface OnFinishedListener {
        fun finishedListener()
    }

    private fun initViews(view: View) {
        buttonSave = view.findViewById(R.id.buttonSave)
        textIL1 = view.findViewById(R.id.text_il1)
        textIL2 = view.findViewById(R.id.text_il2)
        EDtext1 = view.findViewById(R.id.EDtext_1)
        EDtext2 = view.findViewById(R.id.EDtext_2)
    }

    private fun parseIntent() {

        val args = requireArguments()
        if (!args.containsKey(EXTRA_SHOP_ACTIVITY_MODE_ARGS)) {
            throw RuntimeException("Params is absent")
        }
        val mode = args.getString(EXTRA_SHOP_ACTIVITY_MODE_ARGS)

        if ((mode != MODE_ADD) && (mode != MODE_EDIT)) {
            throw RuntimeException("Unknown screen mode ")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ARG)) {
                throw RuntimeException("ShopItem is absent")
            }
            shopItemID = args.getInt(EXTRA_SHOP_ITEM_ARG, UNDEFINED_SHOP_ITEM_ID)
        }
    }

    companion object {
        private const val UNDEFINED_SCREEN_MODE = ""
        private const val UNDEFINED_SHOP_ITEM_ID = -1
        private const val EXTRA_SHOP_ACTIVITY_MODE_ARGS = "args"
        private const val EXTRA_SHOP_ITEM_ARG = "shopItem"
        private const val MODE_ADD = "add"
        private const val MODE_EDIT = "edit"

        fun newFragmentEdit(shopItemID: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments =
                    Bundle().apply {
                        putString(EXTRA_SHOP_ACTIVITY_MODE_ARGS, MODE_EDIT)
                        putInt(EXTRA_SHOP_ITEM_ARG, shopItemID)
                    }
            }
        }

        fun newFragmentAdd(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SHOP_ACTIVITY_MODE_ARGS, MODE_ADD)
                }
            }
        }

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