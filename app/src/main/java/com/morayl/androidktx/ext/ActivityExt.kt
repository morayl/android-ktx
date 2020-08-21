package com.morayl.androidktx.ext

import android.app.Activity
import android.os.Bundle

/**
 * Extrasをnon-nullで取得
 */
fun Activity.requireExtras(): Bundle {
    return requireNotNull(intent?.extras)
}

/**
 * keyを指定して特定の型の値をExtrasから取得
 */
@Suppress("UNCHECKED_CAST")
fun <T> Activity.requireExtra(key: String): T {
    return requireExtras().get(key) as T
}