package com.eericxu.cslibrary

import android.graphics.Path
import android.graphics.PathMeasure
import android.view.animation.Interpolator

class BezierInterpolator : Interpolator {

    val path = Path()
    val measure = PathMeasure()
    var length = 0f
    val pos = FloatArray(2)
    val tan = FloatArray(2)

    init {
//        path.cubicTo(0f, 1.5f, 0.09f, 0.95f, 1f, 1f)
        path.cubicTo(0f, 1.0f, 0.0f, 1f, 1f, 1f)
//        path.cubicTo(0.2f, 0f, 0.0f, 1f, 1f, 1f)
        measure.setPath(path, false)
        length = measure.length

    }

    override fun getInterpolation(input: Float): Float {

        measure.getPosTan(input * length, pos, tan)
        return pos[1]
    }

}