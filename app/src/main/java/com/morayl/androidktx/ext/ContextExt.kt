package com.morayl.androidktx.ext

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.morayl.androidktx.R

fun Context.startActivityIfResolved(
    intent: Intent,
    notResolvedAction: () -> Unit = { Toast.makeText(this, R.string.message_no_apps_to_launch, Toast.LENGTH_SHORT).show() }
) {
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        notResolvedAction()
    }
}