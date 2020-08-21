package com.morayl.androidktx.ext

import android.os.Bundle

/**
 * 指定キーをキャストして返す
 */
@Suppress("UNCHECKED_CAST")
fun <T> Bundle.getAs(key: String): T {
    return get(key) as T
}