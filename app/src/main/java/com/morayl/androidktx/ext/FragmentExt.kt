package com.morayl.androidktx.ext

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
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

fun Fragment.backOrFinishActivity() {
    if (!popBackStack()) {
        activity?.finish()
    }
}

@MainThread
inline fun <reified Args : NavArgs> Fragment.nullableAavArgs() = NavArgsLazy(Args::class) {
    arguments ?: Bundle()
}

fun Fragment.navigate(directions: NavDirections) {
    findNavController().navigate(directions)
}

fun Fragment.navigate(@IdRes id: Int) {
    findNavController().navigate(id)
}

fun Fragment.navigate(directions: NavDirections, navigatorExtras: Navigator.Extras) {
    findNavController().navigate(directions, navigatorExtras)
}

fun Fragment.popBackStack(): Boolean = findNavController().popBackStack()

const val DEFAULT_NAVIGATION_RESULT_KEY = "result"

inline fun <reified T> Fragment.observeNavResult(
    key: String = T::class.simpleName ?: DEFAULT_NAVIGATION_RESULT_KEY,
    clearWhenObserve: Boolean = true,
    crossinline onChanged: (T) -> Unit
) {
    val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
    savedStateHandle?.getLiveData<T>(key)?.observe(viewLifecycleOwner) { t ->
        t ?: return@observe
        if (clearWhenObserve) {
            savedStateHandle.set(key, null)
        }
        onChanged(t)
    }
}

inline fun <reified T> Fragment.setNavResult(result: T, key: String = T::class.simpleName ?: DEFAULT_NAVIGATION_RESULT_KEY) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

inline fun <reified T> Fragment.setNavResultToCurrent(result: T, key: String = T::class.simpleName ?: DEFAULT_NAVIGATION_RESULT_KEY) {
    findNavController().currentBackStackEntry?.savedStateHandle?.set(key, result)
}

inline fun <reified T> Fragment.setNavResult(
    result: T,
    @IdRes target: Int,
    key: String = T::class.simpleName ?: DEFAULT_NAVIGATION_RESULT_KEY
) {
    findNavController().getBackStackEntry(target).savedStateHandle.set(key, result)
}

inline fun <reified T> Fragment.setNavResultAndPopUp(result: T, key: String = T::class.simpleName ?: DEFAULT_NAVIGATION_RESULT_KEY) {
    setNavResult(result, key)
    findNavController().popBackStack()
}

/**
 * popBackStackしてからnavResultをセットする
 * 主にDialogから値をセットする際に利用する
 */
inline fun <reified T> Fragment.popUpAndSetNavResult(result: T, key: String = T::class.simpleName ?: DEFAULT_NAVIGATION_RESULT_KEY) {
    findNavController().popBackStack()
    viewLifecycleOwner.doOnDestroy {
        setNavResultToCurrent(result, key)
    }
}

inline fun <reified T> Fragment.setNavResultAndPopUp(
    result: T,
    @IdRes destination: Int,
    key: String = T::class.simpleName ?: DEFAULT_NAVIGATION_RESULT_KEY,
    inclusive: Boolean = false
) {
    setNavResult(result, destination, key)
    findNavController().popBackStack(destination, inclusive)
}