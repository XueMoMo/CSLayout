package com.eericxu.cslibrary.anim

import android.view.View
import com.eericxu.cslibrary.keyparms.KeyParm

interface BaseAnim<V : View, KP : KeyParm> {
    fun view(): V
    fun from(): KP
    fun to(): KP
}