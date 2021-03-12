package com.morayl.androidktx.toplevel

import java.util.EnumMap

@Suppress("FunctionNaming")
inline fun <reified Key : Enum<Key>, Value> EnumMap(valueBlock: (Key) -> Value): EnumMap<Key, Value> =
    enumValues<Key>().associateWithTo(EnumMap(Key::class.java), valueBlock)
