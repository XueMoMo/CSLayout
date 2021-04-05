package com.eericxu.cslibrary.anim

import android.animation.ValueAnimator
import androidx.core.math.MathUtils
import android.view.View
import com.eericxu.cslibrary.keyparms.KeyParams
import kotlin.math.absoluteValue

class ViewAnim(private val isStart: Boolean,
               private val v: View,
               private val from: KeyParams,
               private val to: KeyParams) : ValueAnimator(), BaseAnim<View, KeyParams> {

    init {
        val start = if (isStart) 1f else 0f
        setFloatValues(start, 1 - start)
        val sh = v.context.resources.displayMetrics.heightPixels
        val sw = v.context.resources.displayMetrics.widthPixels
        val fromX = MathUtils.clamp(from.rect.centerX(), -(from.rect.width()/2), sw + from.rect.width()/2)
        val fromY = MathUtils.clamp(from.rect.centerY(), -(from.rect.height()/2), sh + from.rect.height()/2)
        val translationX = fromX - to.rect.centerX()
        val translationY = fromY - to.rect.centerY()

        val scale = from.rect.width() * 1f / to.rect.width()

        addUpdateListener {
            val value = it.animatedValue as Float
            val clamp = MathUtils.clamp(value, 0f, 1f)
            if (translationX != 0)
                v.translationX = translationX * clamp
            if (translationY != 0)
                v.translationY = translationY * value
            if (scale != 1f) {
                v.scaleX = 1 - (1 - scale) * value
                v.scaleY = 1 - (1 - scale) * value
            }
        }
    }
    fun numSymbol(a: Int) = if (a > 0) 1 else -1
    override fun view() = v
    override fun from() = from
    override fun to() = to

}