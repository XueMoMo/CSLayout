package com.eericxu.cslibrary.anim

import android.view.View
import com.eericxu.cslibrary.keyparms.KeyParams

interface BaseAnim<V : View, KP : KeyParams> {
    fun view(): V
    fun from(): KP
    fun to(): KP
}