package com.morayl.androidktx.ext

import androidx.lifecycle.DefaultLifecycleObserver
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

/**
 * ライフサイクル終了時の処理となるため、副作用が懸念されるような時間のかかる処理等は注意してください
 */
fun LifecycleOwner.doOnDestroy(action: () -> Unit) {
    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            action()
        }
    })
}