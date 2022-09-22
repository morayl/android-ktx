package com.morayl.androidktx.parameter

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel

/**
 * sealed classをNavResultにする場合に実装するクラス
 * sealed class(親クラス)でkeyを実装する
 * observeNavResultは、keyの関係上sealedの親クラスを指定しなければならない
 * 通常クラスは引数型からqualifiedNameを引き出すが、sealed classの場合はset側はsealed具象クラス名、observe側はsealed class名になってしまいkeyが異なってしまうため利用
 */
interface SealedNavResult : Parcelable {
    @IgnoredOnParcel
    val key: String?
}