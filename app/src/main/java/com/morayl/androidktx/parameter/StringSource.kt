package com.morayl.androidktx.parameter

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/**
 * 任意のタイミングでContextを渡してStringを取得できるようにするクラス
 * 利用することで、Contextの引き回しを抑えることができる
 * 各sealed実装クラスはprivateでFakeコンストラクタによって、実装クラスを気にせずStringSource()として使えるようになっています
 */
sealed class StringSource : Parcelable {
    abstract fun getString(context: Context): String

    @Parcelize
    private data class Raw(private val text: String) : StringSource() {
        override fun getString(context: Context): String = text
    }

    @Parcelize
    private data class Resource(@StringRes private val textRes: Int) : StringSource() {
        override fun getString(context: Context): String = context.getString(textRes)
    }

    @Parcelize
    private class FormatResourceSerializable<T : Serializable>(@StringRes private val textRes: Int, private vararg val formatArgs: T) :
        StringSource() {
        @Suppress("SpreadOperator")
        override fun getString(context: Context): String {
            val formatArgs = formatArgs.map { if (it is StringSource) it.getString(context) else it }.toTypedArray()
            return context.getString(textRes, *formatArgs)
        }
    }

    @Parcelize
    private class FormatResourceStringSource(@StringRes private val textRes: Int, private vararg val formatArgs: StringSource) :
        StringSource() {
        @Suppress("SpreadOperator")
        override fun getString(context: Context): String {
            val formatArgs = formatArgs.map { it.getString(context) }.toTypedArray()
            return context.getString(textRes, *formatArgs)
        }
    }

    @Parcelize
    private class StringSourceList(private val list: List<StringSource>) : StringSource() {
        override fun getString(context: Context): String {
            return list.joinToString(separator = "") { it.getString(context) }
        }
    }

    operator fun plus(other: StringSource): StringSource = StringSourceList(listOf(this, other))

    companion object {
        /**
         * Fakeコンストラクタ群。StringSource()として、書くことで、引数によって実態を生成します。
         */
        operator fun invoke(text: String): StringSource = Raw(text)

        operator fun invoke(@StringRes textRes: Int): StringSource = Resource(textRes)

        operator fun <T : Serializable> invoke(@StringRes textRes: Int, vararg formatArgs: T): StringSource =
            FormatResourceSerializable(textRes, *formatArgs)

        operator fun invoke(@StringRes textRes: Int, vararg formatArgs: StringSource): StringSource =
            FormatResourceStringSource(textRes, *formatArgs)
    }
}