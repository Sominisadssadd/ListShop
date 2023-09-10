package com.example.listshop.presentation

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.listshop.R
import com.google.android.material.textfield.TextInputLayout


interface OnEditTextChangedListener {
    fun onEdTextListener()
}

@BindingAdapter("getConvertedFromIntToStringText")
fun getStringTextFromInt(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("getErrorByChangedName")
fun getErrorForTILName(textInputLayout: TextInputLayout, isError: Boolean) {
    if (isError) {
        textInputLayout.error = textInputLayout.context.getString(R.string.error_name_of_product_IL)
    } else {
        textInputLayout.error = null
    }
}


@BindingAdapter("getErrorByChangedCount")
fun getErrorForTILCount(textInputLayout: TextInputLayout, isError: Boolean) {
    if (isError) {
        textInputLayout.error = textInputLayout.context.getString(R.string.error_name_of_count_IL)
    } else {
        textInputLayout.error = null
    }
}

@BindingAdapter("onChangeListener")
fun changeTextViewListener(
    textView: TextView,
    onEditTextChangedListener: OnEditTextChangedListener
) {
    textView.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onEditTextChangedListener.onEdTextListener()
        }

        override fun afterTextChanged(s: Editable?) {
        }
    })
}


