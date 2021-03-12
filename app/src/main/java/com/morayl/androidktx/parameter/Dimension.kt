package com.morayl.androidktx.parameter

import android.content.Context
import androidx.annotation.DimenRes

sealed class Dimension {
    abstract fun getPixelFloat(context: Context): Float

    abstract fun getPixelInt(context: Context): Int

    data class Raw(private val value: Number) : Dimension() {
        override fun getPixelFloat(context: Context): Float = when (value) {
            is Int -> value.toFloat()
            is Float -> value
            else -> error("This class support only Int or Float.")
        }

        override fun getPixelInt(context: Context): Int = when (value) {
            is Int -> value
            is Float -> value.toInt()
            else -> error("This class support only Int or Float.")
        }
    }

    data class Resource(@DimenRes private val dimenRes: Int) : Dimension() {
        override fun getPixelFloat(context: Context): Float = context.resources.getDimension(dimenRes)
        override fun getPixelInt(context: Context): Int = context.resources.getDimensionPixelSize(dimenRes)
    }
}
