/*
 * Created by Ali Al-Sheekh Jassem on 4/21/21 9:16 PM
 * Copyright (c) 2021 . All rights reserved .
 * Last modified 4/21/21 8:37 PM
 */
package magma.global.restaurant.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import magma.global.restaurant.R
import magma.global.restaurant.utils.StringRuleUtil.StringRule
import com.google.android.material.textfield.TextInputLayout

object BindingUtils {
    private const val TAG = "BindingUtils"

    @JvmStatic
    @BindingAdapter("validation", "errorMsg")
    fun setErrorEnable(
        textInputLayout: TextInputLayout,
        stringRule: StringRule,
        errorMsg: String?
    ) {
        if (textInputLayout.editText != null) textInputLayout.editText!!.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    val text = textInputLayout.editText!!.text.toString()
                    val notEmpty =
                        textInputLayout.context.getString(R.string.field_can_not_be_empty)
                    when {
                        text.isEmpty() -> textInputLayout.error =
                            notEmpty
                        stringRule.validate(
                            textInputLayout.editText!!.text
                        ) -> textInputLayout.error = errorMsg
                        else -> textInputLayout.error = null
                    }
                }
            })
    }

}