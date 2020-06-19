package com.eericxu.cslibrary

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.util.Property
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Created by Eericxu on 2018-03-16.
 */
class BackViewGesture(ctx: Context) : GestureDetector.SimpleOnGestureListener(), View.OnTouchListener, View.OnAttachStateChangeListener {
    override fun onViewDetachedFromWindow(v: View?) {

    }

    override fun onViewAttachedToWindow(v: View?) {

    }

    val isNVersion by lazy { Build.VERSION.SDK_INT == Build.VERSION_CODES.N }


    var mView: View? = null


    fun bindToView(csLayout: CSInterface?): BackViewGesture? {
        if (csLayout == null)
            return null
        mView = csLayout as View
        csLayout.setOnTouchListener(this@BackViewGesture)
        mView?.addOnAttachStateChangeListener(this)
        return this
    }

    private val gesture = GestureDetector(ctx, this)
    var onClick: (View) -> Unit = {}
    private var scale = 0.93f
    private var xD = 0f
    private var yD = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        Log.i("onTouch:", "action:${event.action}")
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                xD = event.x
                yD = event.y
            }
            MotionEvent.ACTION_UP -> {
                toRecoverClick()
            }
            MotionEvent.ACTION_CANCEL -> {
                toRecover()
            }
        }
        return gesture.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    //恢复
    private fun toRecover() {
        if (!animReview.isRunning) {
            animReview.removeAllListeners()
            animReview.start()
        }
    }


    private val lis = object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
//            onClick(mView)
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
        }
    }

    //恢复
    private fun toRecoverClick() {
        if (isNVersion) {
//            onClick(mView)
            return
        }
        if (animScale.isRunning) {
            animScale.cancel()
        }
        if (!animReview.isRunning) {
            if (animReview.listeners == null || !animReview.listeners.contains(lis)) {
                animReview.addListener(lis)
            }
            animReview.start()
        }
    }

    //缩放
    private fun toScale() {
        if (isNVersion)
            return
        if (!animScale.isRunning)
            animScale.start()
    }


    private val animScale by lazy {
        val animator = ObjectAnimator.ofFloat(mView, propertyScale, 1f, scale)
        animator.duration = 150
        animator
    }
    private val animReview by lazy {
        val animator = ObjectAnimator.ofFloat(mView, propertyScale, scale, 1f)
        animator.duration = 150
        animator
    }


    private var propertyScale: Property<View, Float> = object : Property<View, Float>(Float::class.java, "scale") {
        override fun set(v: View, value: Float) {
            v.scaleX = value
            v.scaleY = value
        }

        override fun get(v: View): Float {
            return v.scaleX
        }
    }

    fun setCustumScale(scale: Float): BackViewGesture {
        this.scale = scale
        return this
    }

    fun setCustumScale(scaleProperty: Property<View, Float>): BackViewGesture {
        propertyScale = scaleProperty
        return this
    }

    private fun recover() {
        if (animScale.isRunning)
            animScale.cancel()
        mView?.postDelayed({
            mView?.scaleX = 1.0f
            mView?.scaleY = 1.0f
        }, 200)
    }

}