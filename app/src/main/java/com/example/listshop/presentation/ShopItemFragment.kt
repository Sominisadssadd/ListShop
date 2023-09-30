package com.example.listshop.presentation

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.example.listshop.databinding.FragmentShopItemBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.thread

class ShopItemFragment : Fragment() {


    private var screenMode = UNDEFINED_SCREEN_MODE
    private var shopItemID = UNDEFINED_SHOP_ITEM_ID

    @Inject
    lateinit var viewModel: ShopItemActivityViewModel

    private var _binding: FragmentShopItemBinding? = null

    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException(
            "ShopItemFragmentBinding == null"
        )


    private lateinit var onShopItemFinishedListener: OnFinishedListener

    //СВЯЗЬ ФРАГМЕНТА С АКТИВНОСТИ
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


    private val component by lazy {
        (requireActivity().application as ShopListApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentShopItemBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        parseIntent()
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()

        }
        observableEvents()

    }


    private fun launchEditMode() {

        viewModel.getShopItem(shopItemID)
        viewModel.shopitem.observe(viewLifecycleOwner) {
            binding.shopItem = it
        }

        binding.buttonSave.setOnClickListener {
            viewModel.editShopItem(
                shopItemID,
                binding.EDtext1.text.toString(),
                binding.EDtext2.text.toString()
            )
        }
    }

    private fun launchAddMode() {
        binding.buttonSave.setOnClickListener {

            val name = binding.EDtext1.text.toString()
            val count = binding.EDtext2.text.toString()
            viewModel.addShopItem(name, count)

        }
    }

    private fun observableEvents() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.shouldClose.observe(viewLifecycleOwner) {
            onShopItemFinishedListener.finishedListener()
        }
    }

    interface OnFinishedListener {
        fun finishedListener()
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    }


}