package com.eericxu.cslibrary.anim

import android.animation.ValueAnimator
import android.view.View
import com.eericxu.cslibrary.keyparms.KeyParm

class ViewAnim(val isStart: Boolean, val v: View, val from: KeyParm, val to: KeyParm) : ValueAnimator(), BaseAnim<View, KeyParm> {

    init {
        val start = if (isStart) 1f else 0f
        setFloatValues(start, 1 - start)

        val translationX = from.rect.centerX() - to.rect.centerX()
        val translationY = from.rect.centerY() - to.rect.centerY()
        val scale = from.rect.width() * 1f / to.rect.width()

        addUpdateListener {
            val value = it.animatedValue as Float
            if (translationX != 0)
                v.translationX = translationX * value
            if (translationY != 0)
                v.translationY = translationY * value
            if (scale != 1f) {
                v.scaleX = 1 - (1 - scale) * value
                v.scaleY = 1 - (1 - scale) * value
            }
        }
    }

    override fun view(): View {
        return v
    }

    override fun from(): KeyParm {
        return from
    }

    override fun to(): KeyParm {
        return to
    }

}