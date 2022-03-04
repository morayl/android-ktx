package com.morayl.androidktx.ext

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalUnit

/**
 * 日付フォーマット
 */
@Suppress("EnumEntryName")
enum class DateTimeFormat(val value: String) {
    yyyy_MM_dd_E_slash("yyyy/MM/dd(E)"),
    MM_dd_E_slash("MM/dd(E)"),
    yyyy_M_d_E_slash_HH_mm("yyyy/M/d(E) HH:mm"),
    HH_mm_colon("HH:mm"),
}

/**
 * 日付フォーマット定義渡しその表示形式の文字列に変換する
 */
fun LocalDateTime.format(format: DateTimeFormat): String {
    return format(DateTimeFormatter.ofPattern(format.value))
}

fun LocalDateTime.isEqual(other: LocalDateTime, unit: TemporalUnit): Boolean {
    return truncatedTo(unit) == other.truncatedTo(unit)
}

fun LocalDateTime.isAfterOrEqual(other: LocalDateTime, unit: TemporalUnit): Boolean {
    return isEqual(other, unit) || isAfter(other, unit)
}

fun LocalDateTime.isAfter(other: LocalDateTime, unit: TemporalUnit): Boolean {
    return truncatedTo(unit).isAfter(other.truncatedTo(unit))
}

fun LocalDateTime.toEpochMilli(): Long {
    return atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
