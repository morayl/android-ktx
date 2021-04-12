package com.morayl.androidktx.ext

/**
 * Similar to [ifEmpty]
 */
inline fun <C, R> C?.ifNullOrEmpty(defaultValue: () -> R): R where C : R, R : CharSequence =
    if (isNullOrEmpty()) defaultValue() else this
