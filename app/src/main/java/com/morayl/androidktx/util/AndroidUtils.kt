package com.morayl.androidktx.util

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

object AndroidUtils {

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
    fun hasPie() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
    fun hasOreo() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    fun hasTen() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    fun hasTwelve() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
    fun hasThirteen() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}