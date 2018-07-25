package com.eericxu.cslibrary

import android.graphics.Path
import android.graphics.PathMeasure
import android.support.v4.math.MathUtils
import android.util.Log
import android.view.animation.Interpolator

class OffsetInterpolator(
        x1: Float = 0.60f, y1: Float = 0f,
        x2: Float = 0.0f, y2: Float = 1.1f) : Interpolator {

    val path = Path()
    val measure = PathMeasure()
    var length = 0f
    val pos = FloatArray(2)
    val tan = FloatArray(2)
    val xyMap: MutableMap<Float, Float> = mutableMapOf()

    init {
        path.cubicTo(x1, y1, x2, y2, 1f, 1f)
        measure.setPath(path, false)
        length = measure.length

        for (i in 1..100) {
            measure.getPosTan(i * 0.01f * length, pos, tan)
            xyMap[pos[0]] = pos[1]
        }
    }

    override fun getInterpolation(input: Float): Float {
        val value = findMid(input) ?: input
        return value
    }

    private fun findMid(key: Float): Float? {
        val iterator = xyMap.iterator()
        var last: MutableMap.MutableEntry<Float, Float>? = null
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.key == key)
                return next.value
            if (last != null && last.key < key && key < next.key) {
                return (last.value + next.value) / 2
            }
            last = next
        }
        return null
    }
}