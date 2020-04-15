package com.eericxu.cslayout

import androidx.appcompat.app.AppCompatActivity

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