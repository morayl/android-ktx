package com.morayl.androidktx.ext

fun <T> List<T>.indexOfOrNull(t: T): Int? {
    return indexOf(t).takeUnless { it == -1 }
}