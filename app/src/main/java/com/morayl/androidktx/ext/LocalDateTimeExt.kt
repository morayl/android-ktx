package com.morayl.androidktx.ext

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

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
