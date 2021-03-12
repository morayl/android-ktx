package com.morayl.androidktx.parameter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

sealed class ColorValue {
    @ColorInt
    abstract fun getColor(context: Context): Int

    fun getColorDrawable(context: Context): ColorDrawable = ColorDrawable(getColor(context))

    data class Text(private val colorText: String, @ColorRes private val defaultColor: Int) : ColorValue() {
        override fun getColor(context: Context): Int = runCatching { Color.parseColor(colorText) }
            .getOrElse {
                ContextCompat.getColor(context, defaultColor)
            }
    }

    data class Resource(@ColorRes private val colorRes: Int) : ColorValue() {
        override fun getColor(context: Context): Int = ContextCompat.getColor(context, colorRes)
    }

    /**
     * themeによって色が変わります
     * getColorで渡されたcontextにセットされているthemeが適用されます
     */
    data class Attribute(@AttrRes private val colorAttrRes: Int) : ColorValue() {
        override fun getColor(context: Context): Int = TypedValue().apply {
            context.theme.resolveAttribute(colorAttrRes, this, true)
        }.data
    }
}