package com.morayl.androidktx.ext

import androidx.lifecycle.MutableLiveData
import com.morayl.androidktx.util.Event

/**
 * Event型をpostする際にEvent()を省略する
 */
fun <T> MutableLiveData<Event<T>>.postEvent(t: T) {
    postValue(Event(t))
}

/**
 * Event<Unit>型の場合、引数なしでイベントをpostする
 */
fun MutableLiveData<Event<Unit>>.postEvent() {
    postValue(Event(Unit))
}
