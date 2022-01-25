package com.morayl.androidktx.ext

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

fun LifecycleOwner.doOnResume(action: () -> Unit) {
    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            action()
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