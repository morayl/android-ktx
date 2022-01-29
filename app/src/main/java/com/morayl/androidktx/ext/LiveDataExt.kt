package com.morayl.androidktx.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.morayl.androidktx.parameter.Event
import com.morayl.androidktx.parameter.EventObserver

/**
 * valueをnon-nullで取得
 */
fun <T> LiveData<T>.requireValue() = requireNotNull(value)

inline fun <T> MutableLiveData<T>.requireUpdate(function: (T) -> T) {
    value = function(requireValue())
}

/**
 * Event型のLiveDataをobserveし、Eventの中身をハンドリングする
 */
fun <T, U> LiveData<T>.observeEvent(
    lifecycleOwner: LifecycleOwner,
    onEventUnhandledContent: (U) -> Unit
) where T : Event<U> {
    observe(lifecycleOwner, EventObserver {
        onEventUnhandledContent(it)
    })
}

/**
 * 2つのLiveDataを監視し、どちらか変更があれば両方の値を通知
 */
fun <A, B, T> zipLiveData(a: LiveData<A>, b: LiveData<B>, onChanged: (A, B) -> T): LiveData<T> {
    return MediatorLiveData<T>().apply {
        addSource(a) {
            val bValue = b.value
            if (bValue != null) {
                postValue(onChanged(it, bValue))
            }
        }
        addSource(b) {
            val aValue = a.value
            if (aValue != null) {
                postValue(onChanged(aValue, it))
            }
        }
    }
}
