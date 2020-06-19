package com.eericxu.cslibrary

import android.graphics.Path
import android.graphics.PathMeasure
import android.view.animation.Interpolator

class BezierInterpolator(
        x1: Float = 0.00f, y1: Float = 1.10f,
        x2: Float = 0.00f, y2: Float = 0.99f) : Interpolator {

    val path = Path()
    val measure = PathMeasure()
    var length = 0f
    val pos = FloatArray(2)
    val tan = FloatArray(2)

    init {
        path.cubicTo(x1, y1, x2, y2, 1f, 1f)
//        path.cubicTo(0f, 1.0f, 0.0f, 1f, 1f, 1f)
//        path.cubicTo(0.2f, 0f, 0.0f, 1f, 1f, 1f)
        measure.setPath(path, false)
        length = measure.length

    }

    override fun getInterpolation(input: Float): Float {

        measure.getPosTan(input * length, pos, tan)
        return pos[1]
    }

}