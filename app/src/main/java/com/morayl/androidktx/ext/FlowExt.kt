package com.morayl.androidktx.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.morayl.androidktx.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Flowの値をEventのLiveDataに変換
 */
fun <T> Flow<T>.asEventLiveData(
    context: CoroutineContext = EmptyCoroutineContext,
    timeoutInMs: Long = 5000L
): LiveData<Event<T>> = map { Event(it) }.asLiveData(context, timeoutInMs)
