package com.morayl.androidktx.ext

/**
 * castするときに()を挟まずにできるようになる
 */
inline fun <reified T> Any?.castAs(): T? = this as? T

/**
 * castするときに()を挟まずにできるようになる(non-null)
 */
inline fun <reified T> Any?.requireCastAs(): T where T : Any = requireNotNull(castAs())

/**
 * castして処理ブロックを実行し、castした型を返す
 */
inline fun <reified T> Any?.castAlso(block: (t: T) -> Unit): T? {
    if (this is T) {
        block(this)
        return this
    }
    return null
}