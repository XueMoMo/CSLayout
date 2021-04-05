package com.eericxu.cslayout

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.eericxu.cslayout.databinding.AtyCsBinding
import com.eericxu.cslibrary.ScaleViewGesture
import com.eericxu.cslibrary.createAnimator
import com.eericxu.cslibrary.finishShareAnim
import com.eericxu.cslibrary.keyparms.KeyParams
import com.eericxu.cslibrary.startShareAnim

class CSAty : BindBaseAty<AtyCsBinding>() {
    override fun inflate() = AtyCsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind.csLayout.visibility = View.INVISIBLE
        val parm = intent.getParcelableExtra<KeyParams>("imgView")
        val params = bind.ivCover.layoutParams
        val p = Point()
        window.windowManager.defaultDisplay.getSize(p)
        params.height = (p.x * (parm.rect.height() * 1f / parm.rect.width())).toInt()
        bind.ivCover.layoutParams = params
        Glide.with(this)
                .load(intent.getIntExtra("img", R.mipmap.img_1))
                .into(bind.ivCover)

        ScaleViewGesture(this).bindToView(bind.ivCover, bind.csLayout)?.onClick = {
            Toast.makeText(it.context, "Click", Toast.LENGTH_SHORT).show()
        }

        val builder = StringBuilder()
        for (i in 0..100) {
            builder.append("以敦煌为圆心的东北东\n")
        }
        bind.tvContent.text = builder.toString()
    }

    var anim: Animator? = null
    //父类中重写onWindowFocusChanged 当window第一次获取焦点时执行
    override fun onFirstFocus() {
        val animator = createAnimator(true, intent, "imgView", bind.ivCover)
//        (animator as ValueAnimator).addUpdateListener {
//            bind.tvContent.translationY = bind.ivCover.translationY * 0.6f
//            bind.tvContent.translationX = bind.ivCover.translationX
//        }
        bind.csLayout.visibility = View.VISIBLE
        anim = startShareAnim(
                bind.csLayout,
                createAnimator(true, intent, "tvTit", bind.tvTitle),
                animator
        )
    }


    override fun finish() {
        if (anim != null && anim?.isRunning == true)
            return
        val animator = createAnimator(false, intent, "imgView", bind.ivCover)
//        (animator as ValueAnimator).addUpdateListener {
//            bind.tvContent.translationY = bind.ivCover.translationY * 0.6f
//            bind.tvContent.translationX = bind.ivCover.translationX
//        }
        finishShareAnim(
                bind.csLayout,
                createAnimator(false, intent, "tvTit", bind.tvTitle),
                animator,
                onAnimEnd = {
                    superFinish()
                })
    }

    private fun superFinish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}