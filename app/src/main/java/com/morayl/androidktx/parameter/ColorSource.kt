package com.morayl.androidktx.parameter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Parcelable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import kotlinx.parcelize.Parcelize

sealed class ColorSource : Parcelable {
    @ColorInt
    abstract fun getColor(context: Context): Int

    fun getColorDrawable(context: Context): ColorDrawable = ColorDrawable(getColor(context))

    @Parcelize
    data class Text(private val colorText: String, private val defaultColor: ColorSource) : ColorSource() {
        override fun getColor(context: Context): Int = runCatching { Color.parseColor(colorText) }
            .getOrElse {
                defaultColor.getColor(context)
            }
    }

    @Parcelize
    data class Resource(@ColorRes private val colorRes: Int) : ColorSource() {
        override fun getColor(context: Context): Int = ContextCompat.getColor(context, colorRes)
    }

    /**
     * themeによって色が変わります
     * getColorで渡されたcontextにセットされているthemeが適用されます
     */
    @Parcelize
    data class Attribute(@AttrRes private val colorAttrRes: Int) : ColorSource() {
        override fun getColor(context: Context): Int = TypedValue().apply {
            context.theme.resolveAttribute(colorAttrRes, this, true)
        }.data
    }
}