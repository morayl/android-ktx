package com.morayl.androidktx.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.morayl.androidktx.parameter.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Flowの値をEventのLiveDataに変換
 */
fun <T> Flow<T>.asEventLiveData(
    context: CoroutineContext = EmptyCoroutineContext,
    timeoutInMs: Long = 5000L
): LiveData<Event<T>> = map { Event(it) }.asLiveData(context, timeoutInMs)

fun <T> Flow<T>.observe(scope: CoroutineScope, action: (T) -> Unit) {
    onEach { action(it) }.launchIn(scope)
}