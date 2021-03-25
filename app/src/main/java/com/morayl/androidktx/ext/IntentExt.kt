package com.morayl.androidktx.ext

import android.content.Intent

@Suppress("UNCHECKED_CAST")
fun <T> Intent.requireExtra(key: String): T = requireNotNull(extras)[key] as T

@Suppress("UNCHECKED_CAST")
fun <T> Intent.getExtra(key: String): T? = extras?.get(key) as? T