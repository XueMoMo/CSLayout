package com.eericxu.cslayout

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class BindBaseAty<T : ViewBinding> : BaseAty() {
    lateinit var bind: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = inflate()
        setContentView(bind.root)
    }
    abstract fun inflate() : T
}