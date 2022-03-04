package com.morayl.androidktx.ext

import org.threeten.bp.LocalDate

fun LocalDate.isAfterOrEqual(other: LocalDate): Boolean {
    return isEqual(other) || isAfter(other)
}