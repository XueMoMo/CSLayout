@file:Suppress("NOTHING_TO_INLINE")

package com.eericxu.cslibrary

import android.animation.Animator
import android.animation.AnimatorSet
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
                          interpolator: Interpolator = BezierInterpolator()): Animator? {
    return shareAnimMulti(1f, csLayout, animData, duration, interpolator)
}

inline fun finishShareAnim(csLayout: CSLayout?, animData: AnimData, duration: Long = 500,
                           crossinline onAnimEnd: () -> Unit = {},
                           interpolator: Interpolator = BezierInterpolator()) {
    shareAnimMulti(0f, csLayout, animData, duration,interpolator, onAnimEnd)
}

inline fun shareAnim(start: Float, csLayout: CSLayout?, animData: AnimData, duration: Long = 500,
                     crossinline onAnimEnd: () -> Unit = {},
                     interpolator: Interpolator = BezierInterpolator()): Animator? {
    if (csLayout == null || Build.VERSION.SDK_INT < 19)
        return null
    val intArray = IntArray(2)
    csLayout.getLocationInWindow(intArray)
    val topOffset = intArray[1]

    val centerX = csLayout.width / 2f + intArray[0]
    val transationY = animData.rect.top - topOffset
    val transationX = animData.rect.centerX() - centerX


    Log.e("ShareAnim", "height:${csLayout.height} lastHeight:${animData.rect.height()} transationY:$transationY  topOffset:$topOffset")
    val helper = csLayout.csHelper
    val csLWidthOffset = (csLayout.width - animData.rect.width()) / 2f
    val clipB = (csLayout.height - animData.rect.height()) + animData.csParms.mClipB
    val clipL = csLWidthOffset + animData.csParms.mClipL
    val clipR = csLWidthOffset + animData.csParms.mClipR
    val anim = ValueAnimator.ofFloat(start, 1 - start)
            .setDuration(duration)
    anim.interpolator = interpolator
    anim.addUpdateListener {

        val value = anim.animatedValue as Float
        val bais = MathUtils.clamp(value, 0f, 1f)

        csLayout.translationY = transationY * value
        csLayout.translationX = transationX * value
        helper.mClipT = animData.csParms.mClipT * bais
        helper.mClipL = clipL * bais
        helper.mClipR = clipR * bais
        helper.mClipB = clipB * bais

        helper.mCornerLeftTop = animData.csParms.mCornerLeftTop * bais
        helper.mCornerTopRight = animData.csParms.mCornerTopRight * bais
        helper.mCornerRightBottom = animData.csParms.mCornerRightBottom * bais
        helper.mCornerBottomLeft = animData.csParms.mCornerBottomLeft * bais
        csLayout.csHelper.refresh()
        csLayout.invalidate()
    }
    anim.addListener(object : SimpleAnimLis() {
        override fun onAnimationEnd(animation: Animator?) {
            onAnimEnd()
        }
    })
    anim.start()
    return anim
}


inline fun shareAnimMulti(start: Float, csLayout: CSLayout?, animData: AnimData, duration: Long = 500,
                          interpolator: Interpolator = BezierInterpolator(),
                          crossinline onAnimEnd: () -> Unit = {}): Animator? {
    if (csLayout == null || Build.VERSION.SDK_INT < 19)
        return null


    val intArray = IntArray(2)
    csLayout.getLocationInWindow(intArray)
    val helper = csLayout.csHelper
    val clipL = animData.rect.left - intArray[0] + animData.csParms.mClipL
    val clipT = animData.rect.top - intArray[1] + animData.csParms.mClipR
    val clipR = intArray[0] + csLayout.width - animData.rect.right + animData.csParms.mClipR
    val clipB = intArray[1] + csLayout.height - animData.rect.bottom + animData.csParms.mClipB
    val anim = ValueAnimator.ofFloat(start, 1 - start)
            .setDuration(duration)
    anim.interpolator = interpolator
    anim.addUpdateListener {

        val value = anim.animatedValue as Float
        val bais = MathUtils.clamp(value, 0f, 1f)
        helper.mClipL = clipL * bais
        helper.mClipT = clipT * bais
        helper.mClipR = clipR * bais
        helper.mClipB = clipB * bais

        helper.mCornerLeftTop = animData.csParms.mCornerLeftTop * bais
        helper.mCornerTopRight = animData.csParms.mCornerTopRight * bais
        helper.mCornerRightBottom = animData.csParms.mCornerRightBottom * bais
        helper.mCornerBottomLeft = animData.csParms.mCornerBottomLeft * bais
        csLayout.csHelper.refresh()
        csLayout.invalidate()
    }
    anim.addListener(object : SimpleAnimLis() {
        override fun onAnimationEnd(animation: Animator?) {
            onAnimEnd()
        }
    })
    anim.start()
    val set = AnimatorSet()
//    set.play(anim).with()
    return set
}
