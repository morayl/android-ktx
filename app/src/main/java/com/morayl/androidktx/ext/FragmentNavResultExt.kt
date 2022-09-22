package com.morayl.androidktx.ext

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.morayl.androidktx.navigation.NavResultFragment

/**
 * navigationでpopBackStackする際にデータの受け渡しができないのでこちらを利用する。popBackStackの遷移先(戻り先)でデータの受け取り時に利用する想定。
 * （DialogFragmentではうまく動かない可能性があるので使う場合は要検証 https://developer.android.com/guide/navigation/navigation-programmatic#additional_considerations）
 * Unitを受け取る場合は、[observeUnitNavResult]を利用する
 *
 * @param T 流れてくる想定データの型
 * @param onChanged データが流れてきた際に実行したい処理
 */
inline fun <reified T> Fragment.observeNavResult(key: String? = null, crossinline onChanged: (T) -> Unit) {
    val nonNullKey = key ?: NavResultFragment.createKey<T>()
    val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
    savedStateHandle?.getLiveData<T>(nonNullKey)?.observe(viewLifecycleOwner) { t ->
        t ?: return@observe
        // 基本的にクリアするで良いと思うが、必要になった際はクリアするか否かを指定できるよう引数を追加する
        savedStateHandle[nonNullKey] = null
        onChanged(t)
    }
}
