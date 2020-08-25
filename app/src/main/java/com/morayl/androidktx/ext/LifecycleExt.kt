package com.morayl.androidktx.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

fun LifecycleOwner.doOnResume(action: () -> Unit) {
    lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun doOnResume() {
            lifecycle.removeObserver(this)
            action.invoke()
        }
    })
}