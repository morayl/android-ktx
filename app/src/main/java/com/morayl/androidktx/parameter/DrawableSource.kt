package com.morayl.androidktx.parameter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import kotlinx.parcelize.Parcelize

sealed class DrawableSource : Parcelable {

    abstract fun getDrawable(context: Context): Drawable?

    @Parcelize
    data class Resource(@DrawableRes val resId: Int) : DrawableSource() {
        override fun getDrawable(context: Context): Drawable? {
            return AppCompatResources.getDrawable(context, resId)
        }
    }

    @Parcelize
    data class ResourceWithTint(@DrawableRes val resId: Int, val tintColor: ColorSource) : DrawableSource() {
        override fun getDrawable(context: Context): Drawable? {
            return AppCompatResources.getDrawable(context, resId)?.mutate()?.apply {
                setTint(tintColor.getColor(context))
            }
        }
    }

    companion object {
        operator fun invoke(@DrawableRes resId: Int) = Resource(resId)
        operator fun invoke(@DrawableRes resId: Int, tintColor: ColorSource) = ResourceWithTint(resId, tintColor)
    }
}