package com.morayl.androidktx.toplevel

private const val PERCENT_BASE = 100
private const val MESSAGE_NOT_SUPPORTED = "not supported"

@Suppress("ComplexMethod")
fun percent(numerator: Number, denominator: Number): Number = when (numerator) {
    is Int -> when (denominator) {
        is Int -> numerator / denominator * PERCENT_BASE
        is Double -> numerator / denominator * PERCENT_BASE
        is Float -> numerator / denominator * PERCENT_BASE
        is Long -> numerator / denominator * PERCENT_BASE
        is Short -> numerator / denominator * PERCENT_BASE
        is Byte -> numerator / denominator * PERCENT_BASE
        else -> error(MESSAGE_NOT_SUPPORTED)
    }
    is Double -> when (denominator) {
        is Int -> numerator / denominator * PERCENT_BASE
        is Double -> numerator / denominator * PERCENT_BASE
        is Float -> numerator / denominator * PERCENT_BASE
        is Long -> numerator / denominator * PERCENT_BASE
        is Short -> numerator / denominator * PERCENT_BASE
        is Byte -> numerator / denominator * PERCENT_BASE
        else -> error(MESSAGE_NOT_SUPPORTED)
    }
    is Float -> when (denominator) {
        is Int -> numerator / denominator * PERCENT_BASE
        is Double -> numerator / denominator * PERCENT_BASE
        is Float -> numerator / denominator * PERCENT_BASE
        is Long -> numerator / denominator * PERCENT_BASE
        is Short -> numerator / denominator * PERCENT_BASE
        is Byte -> numerator / denominator * PERCENT_BASE
        else -> error(MESSAGE_NOT_SUPPORTED)
    }
    is Long -> when (denominator) {
        is Int -> numerator / denominator * PERCENT_BASE
        is Double -> numerator / denominator * PERCENT_BASE
        is Float -> numerator / denominator * PERCENT_BASE
        is Long -> numerator / denominator * PERCENT_BASE
        is Short -> numerator / denominator * PERCENT_BASE
        is Byte -> numerator / denominator * PERCENT_BASE
        else -> error(MESSAGE_NOT_SUPPORTED)
    }
    is Short -> when (denominator) {
        is Int -> numerator / denominator * PERCENT_BASE
        is Double -> numerator / denominator * PERCENT_BASE
        is Float -> numerator / denominator * PERCENT_BASE
        is Long -> numerator / denominator * PERCENT_BASE
        is Short -> numerator / denominator * PERCENT_BASE
        is Byte -> numerator / denominator * PERCENT_BASE
        else -> error(MESSAGE_NOT_SUPPORTED)
    }
    is Byte -> when (denominator) {
        is Int -> numerator / denominator * PERCENT_BASE
        is Double -> numerator / denominator * PERCENT_BASE
        is Float -> numerator / denominator * PERCENT_BASE
        is Long -> numerator / denominator * PERCENT_BASE
        is Short -> numerator / denominator * PERCENT_BASE
        is Byte -> numerator / denominator * PERCENT_BASE
        else -> error(MESSAGE_NOT_SUPPORTED)
    }
    else -> error(MESSAGE_NOT_SUPPORTED)
}