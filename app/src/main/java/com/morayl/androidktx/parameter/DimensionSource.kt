package com.morayl.androidktx.parameter

import android.content.Context
import androidx.annotation.DimenRes

sealed class DimensionSource {
    abstract fun getPixelFloat(context: Context): Float

    abstract fun getPixelInt(context: Context): Int

    data class Raw(private val value: Number) : DimensionSource() {
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

    data class Resource(@DimenRes private val dimenRes: Int) : DimensionSource() {
        override fun getPixelFloat(context: Context): Float = context.resources.getDimension(dimenRes)
        override fun getPixelInt(context: Context): Int = context.resources.getDimensionPixelSize(dimenRes)
    }

    private data class DimensionList(private val list: List<DimensionWithOperator>) : DimensionSource() {
        override fun getPixelFloat(context: Context): Float {
            return list.map {
                val value = it.dimension.getPixelFloat(context)
                if (it.operatorType == OperatorType.MINUS) {
                    -value
                } else {
                    value
                }
            }.sum()
        }

        override fun getPixelInt(context: Context): Int {
            return list.sumOf {
                val value = it.dimension.getPixelInt(context)
                if (it.operatorType == OperatorType.MINUS) {
                    -value
                } else {
                    value
                }
            }
        }
    }

    private data class DimensionWithOperator(val dimension: DimensionSource, val operatorType: OperatorType? = null)

    private enum class OperatorType {
        PLUS,
        MINUS,
    }

    operator fun plus(other: DimensionSource): DimensionSource {
        return DimensionList(listOf(DimensionWithOperator(this), DimensionWithOperator(other, OperatorType.PLUS)))
    }

    operator fun minus(other: DimensionSource): DimensionSource {
        return DimensionList(listOf(DimensionWithOperator(this), DimensionWithOperator(other, OperatorType.MINUS)))
    }
}
