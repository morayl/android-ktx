package com.morayl.androidktx.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
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

/**
 * Flowをlifecycleで安全にobserveするためのメソッド
 *
 * 作成背景
 * launchIn(viewLifecycleScope)だと、onPauseなどでonEachに来て、そこでFragment遷移をするとExceptionになる
 * →ex. 情報登録画面で登録ボタンを押して登録通信をして、通信完了したら前の画面に戻ってSnackBarを出す(通信完了前に画面中断すると、Exception)
 * repeatOnLifecycleだと、上記onPauseの問題は防げるが、pause状態でSharedFlowが発火しても復帰時に発火しない
 * →ex. 情報登録画面で登録ボタン押下後、中断→再開すると登録前の画面のままになる
 * また、stateFlowを購読すると、復帰時にもう一度onEachに来てしまう(見た目上問題なくても無駄なUI構築処理が走ったりする)
 * よって、onPause以降でイベントが来ない、かつ、復帰時にイベントが来てほしいため、このメソッドを作成した
 */
fun <T> Flow<T>.observe(owner: LifecycleOwner, action: (T) -> Unit) {
    onEach {
        owner.doOnResume {
            action(it)
        }
    }.launchIn(owner.lifecycleScope)
}
