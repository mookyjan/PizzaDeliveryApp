package com.mudassir.core

import android.content.Context
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.Toast


var View.visible
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

fun View.hide(gone: Boolean = true) {
    visibility = if (gone) GONE else INVISIBLE
}

fun ImageView.hide(gone: Boolean = true) {
      visibility = if (gone) GONE else INVISIBLE
}

fun ImageView.show() {
    visibility = VISIBLE
}

fun View.show() {
    visibility = VISIBLE
}

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}




