package com.morayl.androidktx.parameter

import android.content.Context
import androidx.annotation.StringRes

sealed class Message {
    abstract fun getText(context: Context): String

    data class Text(private val text: String) : Message() {
        override fun getText(context: Context): String = text
    }

    data class Resource(@StringRes private val textRes: Int) : Message() {
        override fun getText(context: Context): String = context.getString(textRes)
    }

    class ResourceText(@StringRes private val textRes: Int, private vararg val formatArgs: Any) : Message() {
        @Suppress("SpreadOperator")
        override fun getText(context: Context): String = context.getString(textRes, *formatArgs)
    }

    companion object {
        // これによりMessage("sample")と書けるようになり、Message.Text("sample")とTextを意識しなくて良くなります
        operator fun invoke(text: String) = Text(text)

        // これによりMessage(R.string.sample)と書けるようになり、Message.Resource(R.string.sample)とResourceを意識しなくて良くなります
        operator fun invoke(@StringRes textRes: Int) = Resource(textRes)

        // これによりMessage(R.string.sample, "sample")と書けるようになり、Message.ResourceText(R.string.sample, "sample")とResourceTextを意識しなくて良くなります
        operator fun invoke(@StringRes textRes: Int, vararg formatArgs: Any) = ResourceText(textRes, *formatArgs)
    }
}