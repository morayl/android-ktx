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

    /**
     * TのnavigationId。基本はそのFragmentのNavigationIdだが、子Fragmentなどで親のNavDirectionを利用している場合は親FragmentのnavigationIdを指定する。
     * 連打などにより自分のDestination以外から自分が持つDirectionに遷移しようとしてクラッシュする問題を防ぐために作成
     */
    val navigationRootIds: List<Int>

    fun Fragment.navigate(navOptions: NavOptions? = null, action: T.() -> NavDirections) {
        val navigationRootIds = navigationRootIds
        if (findNavController().currentDestination?.id?.let { navigationRootIds.contains(it) } == false) {
            // navigationRootIdsに含まれていなければ、遷移しない
            return
        }
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
    findNavController().navigate(directions, navOptions)
}
