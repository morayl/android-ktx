package com.morayl.androidktx.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.morayl.androidktx.parameter.StringSource

@BindingAdapter("stringSource")
fun TextView.setStringSource(source: StringSource) {
    text = source.getString(context)
}