package com.eericxu.cslibrary

import android.content.Context
import android.graphics.*
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.lang.Exception

class CSHelper {

    var mCircle = false             //是否圆形
    var mCornerOverlay = true      //覆盖模式
    var mCornerOverlayColor = Color.WHITE
    var mCorner = 0f                //圆角
    var mCornerLeftTop = 0f
    var mCornerTopRight = 0f
    var mCornerRightBottom = 0f
    var mCornerBottomLeft = 0f
    var mShadowSize = 0f            //阴影长度
    var mShadowSizeLeft = 0f        //
    var mShadowSizeTop = 0f
    var mShadowSizeRight = 0f
    var mShadowSizeBottom = 0f

    private var shadowColorChanged = true
    var mShadowColor: Int = 0x44000000            //阴影颜色
        set(value) {
            if (value == field)
                return
            field = value
            shadowColorChanged = true
        }
    var mRealShadowSize = 0f


    var mClip = 0f
    var mClipL = 0f
    var mClipT = 0f
    var mClipR = 0f
    var mClipB = 0f

    val mPaint = Paint()
    var radii = FloatArray(8)   // top-left, top-right, bottom-right, bottom-left
    var mShadowBitmap: Bitmap? = null//阴影Bitmap
    var mClipPath = Path()          //View区域
    var mShadowPath = Path()        // 阴影区域
    var mRectClip = RectF()
    var mRectShadow = RectF()
    var mRect = RectF()
    var mWidth = 0
    var mHeight = 0

    init {
        mPaint.isAntiAlias = true
//        mPaint.strokeWidth = 0f

    }


    fun initAttr(view: View, ctx: Context?, arrts: AttributeSet?) {
        if (ctx == null || arrts == null)
            return
        val array = ctx.obtainStyledAttributes(arrts, R.styleable.CSAttrs)
        try {
            array?.apply {
                mCorner = getDimensionPixelSize(R.styleable.CSAttrs_cs_corner, 0).toFloat()
                val corner = mCorner.toInt()
                mCornerLeftTop = getDimensionPixelSize(R.styleable.CSAttrs_cs_corner_top_left, corner).toFloat()
                mCornerTopRight = getDimensionPixelSize(R.styleable.CSAttrs_cs_corner_top_right, corner).toFloat()
                mCornerRightBottom = getDimensionPixelSize(R.styleable.CSAttrs_cs_corner_bottom_right, corner).toFloat()
                mCornerBottomLeft = getDimensionPixelSize(R.styleable.CSAttrs_cs_corner_bottom_left, corner).toFloat()


                mCircle = getBoolean(R.styleable.CSAttrs_cs_circle, mCircle)

                mShadowColor = getColor(R.styleable.CSAttrs_cs_shadow_color, mShadowColor)
                mShadowSize = getDimensionPixelSize(R.styleable.CSAttrs_cs_shadow_size, 0).toFloat()
                val shadowSize = mShadowSize.toInt()
                mShadowSizeLeft = getDimensionPixelSize(R.styleable.CSAttrs_cs_shadow_size_left, shadowSize).toFloat()
                mShadowSizeTop = getDimensionPixelSize(R.styleable.CSAttrs_cs_shadow_size_top, shadowSize).toFloat()
                mShadowSizeRight = getDimensionPixelSize(R.styleable.CSAttrs_cs_shadow_size_right, shadowSize).toFloat()
                mShadowSizeBottom = getDimensionPixelSize(R.styleable.CSAttrs_cs_shadow_size_bottom, shadowSize).toFloat()

                mCornerOverlay = getBoolean(R.styleable.CSAttrs_cs_corner_overlay, mCornerOverlay)
                mCornerOverlayColor = getColor(R.styleable.CSAttrs_cs_corner_overlay_color, mCornerOverlayColor)

                mClip = getDimensionPixelSize(R.styleable.CSAttrs_cs_clip, 0).toFloat()
                val clip = mClip.toInt()
                mClipL = getDimensionPixelSize(R.styleable.CSAttrs_cs_clip_left, clip).toFloat()
                mClipT = getDimensionPixelSize(R.styleable.CSAttrs_cs_clip_top, clip).toFloat()
                mClipR = getDimensionPixelSize(R.styleable.CSAttrs_cs_clip_right, clip).toFloat()
                mClipB = getDimensionPixelSize(R.styleable.CSAttrs_cs_clip_bottom, clip).toFloat()
            }
            array?.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var canvas: Canvas? = null
    private fun createShader() {
        if (mRealShadowSize <= 0f)
            return
        if (!shadowColorChanged)
            return
        shadowColorChanged = false
        if (mShadowBitmap == null || canvas == null) {
            canvas = Canvas()
            mShadowBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888)
            canvas?.setBitmap(mShadowBitmap)
        }
        mPaint.color = Color.TRANSPARENT
        canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        mPaint.setShadowLayer(mRealShadowSize, 0f, 0f, mShadowColor)
        canvas?.drawPath(mShadowPath, mPaint)

    }

    fun onSizeChange(view: View, w: Int, h: Int) {
        mWidth = w
        mHeight = h
        computePath()
        createShader()

    }

    private fun computePath() {
        radii[0] = mCornerLeftTop
        radii[1] = mCornerLeftTop

        radii[2] = mCornerTopRight
        radii[3] = mCornerTopRight

        radii[4] = mCornerRightBottom
        radii[5] = mCornerRightBottom

        radii[6] = mCornerBottomLeft
        radii[7] = mCornerBottomLeft
        mClipPath.reset()
        mShadowPath.reset()
        mRect.set(0f, 0f, 0f + mWidth, 0f + mHeight)
        if (mCircle) {
            val circleA = Math.min(mWidth - mShadowSize * 2, mHeight - mShadowSize * 2)
            val left = (mWidth - circleA) / 2f
            val top = (mHeight - circleA) / 2f
            val right = left + circleA
            val bottom = top + circleA
            mRectClip.set(left, top, right, bottom)
            mClipPath.addRoundRect(mRectClip, circleA / 2f, circleA / 2f, Path.Direction.CW)
            mRealShadowSize = mShadowSize
            mRectShadow.set(mRectClip)
            mShadowPath.addRoundRect(mRectShadow, circleA / 2f, circleA / 2f, Path.Direction.CW)
        } else {
            mRectClip.set(mClipL, mClipT, mWidth - mClipR, mHeight - mClipB)
            mClipPath.addRoundRect(mRectClip, radii, Path.Direction.CW)

            val max = max(mShadowSize, mShadowSizeLeft, mShadowSizeTop, mShadowSizeRight, mShadowSizeBottom)
            mRealShadowSize = max
            mRectShadow.set(
                    max - mShadowSizeLeft + mClipL,
                    max - mShadowSizeTop + mClipT,
                    mWidth - (max - mShadowSizeRight + mClipR),
                    mHeight - (max - mShadowSizeBottom + mClipB))
            mShadowPath.addRoundRect(mRectShadow, radii, Path.Direction.CW)
        }
        val ageFix = 10f
        mClipPath.moveTo(-ageFix, -ageFix)
        mClipPath.moveTo(mWidth + ageFix, mHeight + ageFix)
    }


    fun refresh() {
//        val startTime = System.currentTimeMillis()
        computePath()
//        Log.e("Time::", "compute:${System.currentTimeMillis() - startTime}")
        createShader()
    }

    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SCREEN)
    fun drawBefore(c: Canvas?, isEditMode: Boolean = false) {
        if (c == null)
            return
        c.save()
        if (isEditMode) {
            c.clipPath(mClipPath)
            return
        }
        if (mCornerOverlay) return
        c.clipPath(mClipPath)

    }


    fun drawAfter(c: Canvas?, isEditMode: Boolean = false) {
        if (c == null) return
        if (mCornerOverlay && !isEditMode) {
            mClipPath.fillType = Path.FillType.INVERSE_WINDING
            mPaint.color = mCornerOverlayColor
            mPaint.style = Paint.Style.FILL
            c.drawPath(mClipPath, mPaint)
        }
        if (mCorner > 0) {
            mPaint.strokeWidth = 1f
            mPaint.style = Paint.Style.STROKE
            mPaint.color = Color.WHITE
            mPaint.xfermode = xfermode
            c.drawPath(mClipPath, mPaint)
            mPaint.xfermode = null
        }
        c.restore()
        if (mRealShadowSize > 0 && !isEditMode) {
            c.save()
            mClipPath.fillType = Path.FillType.WINDING
//            Log.e("drawShadow:", "" + mRealShadowSize)
            c.clipPath(mClipPath, Region.Op.DIFFERENCE)
            mPaint.color = Color.WHITE
            mPaint.style = Paint.Style.FILL
            c.drawBitmap(mShadowBitmap, 0f, 0f, mPaint)
            c.restore()
        }
    }

    fun max(vararg value: Float): Float {
        var max = value[0]
        value.forEach {
            if (it > max)
                max = it
        }
        return max
    }


}