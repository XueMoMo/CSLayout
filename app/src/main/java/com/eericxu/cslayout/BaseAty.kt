package com.eericxu.cslayout

import android.support.v7.app.AppCompatActivity

open class BaseAty : AppCompatActivity() {
    private var isFirstFocus = true
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (isFirstFocus && hasFocus) {
            onFirstFocus()
            isFirstFocus = false
        }
    }

    open fun onFirstFocus() {}
}