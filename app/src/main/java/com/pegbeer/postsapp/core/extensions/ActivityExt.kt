package com.pegbeer.postsapp.core.extensions

import android.app.Activity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pegbeer.postsapp.R

fun Activity.showErrorDialog(){
    MaterialAlertDialogBuilder(this)
        .setTitle(R.string.error)
        .setMessage(R.string.generic_error_message)
        .setCancelable(false)
        .setPositiveButton(R.string.cancel){ v, _ ->
            v.cancel()
        }.show()
}