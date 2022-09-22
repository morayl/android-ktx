package com.morayl.androidktx.ext

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.popBackStack(): Boolean {
    return findNavController().popBackStack()
}

fun Fragment.popBackStack(@IdRes destination: Int, inclusive: Boolean = false): Boolean {
    return findNavController().popBackStack(destination, inclusive)
}
