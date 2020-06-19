package com.eericxu.cslibrary

import android.content.Context
import android.graphics.Canvas
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet

class CSLayout : ConstraintLayout,CSInterface {
    override fun csHelper(): CSHelper {
        return csHelper
    }

    val csHelper = CSHelper()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        csHelper.initAttr(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        csHelper.initAttr(context, attrs)
    }

    private var isDrawled = false
    override fun draw(canvas: Canvas?) {
        isDrawled = true
        csHelper.drawBefore(canvas,isInEditMode)
        super.draw(canvas)
        csHelper.drawAfter(canvas,isInEditMode)

    }

    override fun dispatchDraw(canvas: Canvas?) {
        if (isDrawled)
            super.dispatchDraw(canvas)
        else {
            csHelper.drawBefore(canvas,isInEditMode)
            super.dispatchDraw(canvas)
            csHelper.drawAfter(canvas,isInEditMode)
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        csHelper.onSizeChange(w, h)
    }
}