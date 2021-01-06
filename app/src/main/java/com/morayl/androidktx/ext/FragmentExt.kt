package com.morayl.androidktx.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.properties.ReadOnlyProperty

/**
 * Fragmentのargumentを型にキャストして取得
 */
@Suppress("UNCHECKED_CAST")
fun <T> Fragment.getArgument(key: String): T? {
    return arguments?.get(key) as? T
}

/**
 * Fragmentのargumentを型にキャストして取得(non-null)
 * 移譲プロパティにも対応
 */
@Suppress("UNCHECKED_CAST")
fun <T> Fragment.requireArgument(key: String): ReadOnlyProperty<Fragment, T> =
    ReadOnlyProperty { _, _ -> requireArguments().get(key) as T }

/**
 * AACのViewModelをFactoryクラス不要で生成する
 */
inline fun <reified VM : ViewModel> Fragment.getViewModel(crossinline factory: () -> VM): VM {
    return ViewModelProvider(this, object : ViewModelProvider.Factory {
        override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
            return requireNotNull(modelClass.cast(factory()))
        }
    }).get(VM::class.java)
}

/**
 * [Fragment.getViewModel]をPropertyDelegation(by)で利用する
 */
inline fun <reified VM : ViewModel> Fragment.viewModelProvider(crossinline factory: () -> VM) =
    lazy { getViewModel { factory() } }
