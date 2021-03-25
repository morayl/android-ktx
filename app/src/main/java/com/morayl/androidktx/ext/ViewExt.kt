package com.morayl.androidktx.ext

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager

fun View.closeIME() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

inline fun View.afterMeasured(crossinline predicate: View.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredHeight > 0 && measuredWidth > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                predicate()
            }
        }
    })
}