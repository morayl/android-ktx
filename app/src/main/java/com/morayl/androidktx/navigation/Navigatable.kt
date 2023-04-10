package com.morayl.androidktx.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

/**
 * navigateをするFragmentが実装するinterface
 * Navigatable<XXFragmentDirections.Companion>として利用します
 */
interface Navigatable<T> {
    val navDirections: T

    fun Fragment.navigate(navOptions: NavOptions? = null, action: T.() -> NavDirections) {
        navigate(action(navDirections), navOptions)
    }

    fun Fragment.navigateWithPopUpTo(
        @IdRes popUpToDestination: Int = requireNotNull(findNavController().currentDestination).id,
        inclusive: Boolean = false,
        action: T.() -> NavDirections
    ) {
        navigate(action(navDirections), NavOptions.Builder().setPopUpTo(popUpToDestination, inclusive).build())
    }
}

private fun Fragment.navigate(directions: NavDirections, navOptions: NavOptions? = null) {
    try {
        findNavController().navigate(directions, navOptions)
    } catch (ignore: IllegalArgumentException) {
        // リストなどで同時押しされて遷移する場合(例：FragmentA→FragmentB)、一回目のクリック処理でBに遷移した後二回目のクリック処理時にBに遷移しようとする
        // が、すでにBに遷移しているため、B→Bの遷移が定義されていないとIllegalArgumentExceptionが発生する
        // Exceptionを用いずに、findNavController().currentDestination?.getAction(directions.actionId) ?: returnという方法でも回避できる
    }
}
