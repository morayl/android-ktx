package com.morayl.androidktx.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.openIME() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    requestFocus()
    inputMethodManager.showSoftInput(this, 0)
}