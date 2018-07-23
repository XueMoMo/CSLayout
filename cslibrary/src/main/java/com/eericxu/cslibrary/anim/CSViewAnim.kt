package com.eericxu.cslibrary.anim

import android.animation.ValueAnimator
import android.support.v4.math.MathUtils
import android.view.View
import com.eericxu.cslibrary.CSInterface
import com.eericxu.cslibrary.keyparms.CSKeyParm

class CSViewAnim(val isStart: Boolean, val v: CSInterface, val from: CSKeyParm, val to: CSKeyParm) : ValueAnimator(), BaseAnim<View, CSKeyParm> {
    val view = v as View

    init {
        val start = if (isStart) 1f else 0f
        setFloatValues(start, 1 - start)
        val clipL = from.rect.left - to.rect.left + from.csParms.mClipL
        val clipT = from.rect.top - to.rect.top + from.csParms.mClipT
        val clipR = to.rect.right - from.rect.right + from.csParms.mClipR
        val clipB = to.rect.bottom - from.rect.bottom + from.csParms.mClipB
        addUpdateListener { anim ->
            val value = anim.animatedValue as Float
            val bais = MathUtils.clamp(value, 0f, 1f)
            v.csHelper().mClipL = clipL * bais
            v.csHelper().mClipT = clipT * bais
            v.csHelper().mClipR = clipR * bais
            v.csHelper().mClipB = clipB * bais

            v.csHelper().mCornerLeftTop = from.csParms.mCornerLeftTop* bais
            v.csHelper().mCornerTopRight = from.csParms.mCornerTopRight * bais
            v.csHelper().mCornerRightBottom = from.csParms.mCornerRightBottom * bais
            v.csHelper().mCornerBottomLeft = from.csParms.mCornerBottomLeft * bais
            v.csHelper().refresh()
            view.invalidate()
        }
    }


    override fun view() = view
    override fun from() = from
    override fun to() = to

}