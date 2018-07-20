@file:Suppress("NOTHING_TO_INLINE")

package com.eericxu.cslibrary

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Rect
import android.os.Build
import android.support.v4.math.MathUtils
import android.util.Log
import android.view.View
import android.view.animation.Interpolator

inline fun createAnimData(v: View, csHelper: CSHelper): AnimData {
    val rect = Rect()
    val intArray = IntArray(2)
    v.getLocationInWindow(intArray)
    intArray[0]
    rect.set(intArray[0], intArray[1], intArray[0] + v.width, intArray[1] + v.height)

    val csParms = CSParms()
    csParms.mClipL = csHelper.mClipL
    csParms.mClipT = csHelper.mClipT
    csParms.mClipR = csHelper.mClipR
    csParms.mClipB = csHelper.mClipB

    csParms.mCornerLeftTop = csHelper.mCornerLeftTop
    csParms.mCornerTopRight = csHelper.mCornerTopRight
    csParms.mCornerRightBottom = csHelper.mCornerRightBottom
    csParms.mCornerBottomLeft = csHelper.mCornerBottomLeft

    return AnimData(rect, csParms)
}


inline fun startShareAnim(csLayout: CSLayout?, animData: AnimData, duration: Long = 600,
                          interpolator: Interpolator = BezierInterpolator()): ValueAnimator? {
    if (csLayout == null || Build.VERSION.SDK_INT < 19)
        return null
    val intArray = IntArray(2)
    csLayout.getLocationInWindow(intArray)
    val topOffset = intArray[1]
    val transationY = animData.rect.top - topOffset
//    Log.e("ShareAnim", "transationY:$transationY  topOffset:$topOffset")
    val helper = csLayout.csHelper
    val clipB = (csLayout.height - animData.rect.height()) + animData.csParms.mClipB
    val anim = ValueAnimator.ofFloat(1f, 0f)
            .setDuration(duration)
    anim.interpolator = interpolator
    anim.addUpdateListener {

        val value = anim.animatedValue as Float
        val bais = MathUtils.clamp(value,0f,1f)
//        Log.i("bais","bais:$bais")
        csLayout.translationY = transationY * value
        helper.mClipL = animData.csParms.mClipL * bais
        helper.mClipT = animData.csParms.mClipT * bais
        helper.mClipR = animData.csParms.mClipR * bais
        helper.mClipB = clipB * bais

        helper.mCornerLeftTop = animData.csParms.mCornerLeftTop * bais
        helper.mCornerTopRight = animData.csParms.mCornerTopRight * bais
        helper.mCornerRightBottom = animData.csParms.mCornerRightBottom * bais
        helper.mCornerBottomLeft = animData.csParms.mCornerBottomLeft * bais
        csLayout.csHelper.refresh()
        csLayout.invalidate()

    }
    anim.start()
    return anim
}

inline fun finishShareAnim(csLayout: CSLayout?, animData: AnimData, duration: Long = 500,
                           crossinline onAnimEnd: () -> Unit = {},
                           interpolator: Interpolator = BezierInterpolator()) {
    if (csLayout == null || Build.VERSION.SDK_INT < 19) {
        onAnimEnd()
        return
    }

    val intArray = IntArray(2)
    csLayout.getLocationInWindow(intArray)
    val topOffset = intArray[1]
    val transationY = animData.rect.top - topOffset
//    Log.e("ShareAnim", "transationY:$transationY  topOffset:$topOffset")
    val helper = csLayout.csHelper
    val clipB = (csLayout.height - animData.rect.height()) + animData.csParms.mClipB
    val anim = ValueAnimator.ofFloat(0f, 1f)
            .setDuration(duration)
    anim.interpolator = interpolator
    anim.addUpdateListener {

        val value = anim.animatedValue as Float
        val bais = Math.max(value,0f)
        csLayout.translationY = transationY * value
        helper.mClipL = animData.csParms.mClipL * bais
        helper.mClipT = animData.csParms.mClipT * bais
        helper.mClipR = animData.csParms.mClipR * bais
        helper.mClipB = clipB * bais

        helper.mCornerLeftTop = animData.csParms.mCornerLeftTop * bais
        helper.mCornerTopRight = animData.csParms.mCornerTopRight * bais
        helper.mCornerRightBottom = animData.csParms.mCornerRightBottom * bais
        helper.mCornerBottomLeft = animData.csParms.mCornerBottomLeft * bais
        csLayout.csHelper.refresh()
        csLayout.invalidate()
    }
    anim.addListener(object :SimpleAnimLis(){
        override fun onAnimationEnd(animation: Animator?) {
            onAnimEnd()
        }
    })
    anim.start()

}
