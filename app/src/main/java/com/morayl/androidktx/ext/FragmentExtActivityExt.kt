package com.morayl.androidktx.ext

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * AACのViewModelをFactoryクラス不要で生成する
 */
inline fun <reified VM : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> VM): VM {
    return ViewModelProvider(this, object : ViewModelProvider.Factory {
        override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
            return requireNotNull(modelClass.cast(factory()))
        }
    }).get(VM::class.java)
}

/**
 * [FragmentActivity.getViewModel]をPropertyDelegation(by)で利用する
 */
inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(crossinline factory: () -> VM) =
    lazy { getViewModel { factory() } }
