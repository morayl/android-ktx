package com.morayl.androidktx.toplevel

import java.util.EnumMap

@Suppress("FunctionNaming")
inline fun <reified Key : Enum<Key>, Value> EnumMap(entries: List<Key>? = null, valueBlock: (Key) -> Value): EnumMap<Key, Value> {
    return if (entries != null) {
        enumValues<Key>().filter { entries.contains(it) }.associateWithTo(EnumMap(Key::class.java), valueBlock)
    } else {
        enumValues<Key>().associateWithTo(EnumMap(Key::class.java), valueBlock)
    }
}
