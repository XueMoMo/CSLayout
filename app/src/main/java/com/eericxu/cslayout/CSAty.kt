package com.eericxu.cslayout

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Point
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.eericxu.cslibrary.ScaleViewGesture
import com.eericxu.cslibrary.createAnimator
import com.eericxu.cslibrary.finishShareAnim
import com.eericxu.cslibrary.keyparms.KeyParm
import com.eericxu.cslibrary.startShareAnim
import kotlinx.android.synthetic.main.aty_cs.*

class CSAty : BaseAty() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aty_cs)

        val parm = intent.getParcelableExtra<KeyParm>("imgView")
        val params = iv_cover.layoutParams
        val p = Point()
        window.windowManager.defaultDisplay.getSize(p)
        params.height = (p.x * (parm.rect.height() * 1f / parm.rect.width())).toInt()
        iv_cover.layoutParams = params
        Glide.with(this)
                .load(intent.getIntExtra("img", R.mipmap.img_1))
                .into(iv_cover)

        ScaleViewGesture(this).bindToView(iv_cover, csLayout)?.onClick = {
            Toast.makeText(it.context, "Click", Toast.LENGTH_SHORT).show()
        }

        val builder = StringBuilder()
        for (i in 0..100) {
            builder.append("以敦煌为圆心的东北东\n")
        }
        tv_content.text = builder.toString()
    }

    var firstFocus = true
    var anim: Animator? = null
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (firstFocus && hasFocus) {
            val animator = createAnimator(true, intent, "imgView", iv_cover)
            (animator as ValueAnimator).addUpdateListener {
                tv_content.translationY = iv_cover.translationY * 0.6f
                tv_content.translationX = iv_cover.translationX
            }

            anim = startShareAnim(
                    csLayout,
                    createAnimator(true, intent, "tvTit", tv_title),
                    animator
            )
        }
        firstFocus = false
    }

    override fun finish() {
        if (anim != null && anim?.isRunning == true)
            return

        val animator = createAnimator(false, intent, "imgView", iv_cover)
        (animator as ValueAnimator).addUpdateListener {
            tv_content.translationY = iv_cover.translationY * 0.6f
            tv_content.translationX = iv_cover.translationX
        }
        finishShareAnim(
                csLayout,
                createAnimator(false, intent, "tvTit", tv_title),
                animator,
                onAnimEnd = {
                    superFinish()
                })
    }

    fun superFinish() {
        super.finish()
        overridePendingTransition(0, R.anim.exit_fade)
    }
}