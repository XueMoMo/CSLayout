package com.eericxu.cslayout

import android.animation.ValueAnimator
import android.os.Bundle
import com.bumptech.glide.Glide
import com.eericxu.cslibrary.AnimData
import com.eericxu.cslibrary.finishShareAnim
import com.eericxu.cslibrary.startShareAnim
import kotlinx.android.synthetic.main.aty_cs.*

class CSAty : BaseAty() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aty_cs)

        val height = animData.rect.height()
        iv_cover.layoutParams.height = height
        iv_cover.layoutParams = iv_cover.layoutParams
        Glide.with(this)
                .load(intent.getIntExtra("img", R.mipmap.img_1))
                .into(iv_cover)
    }

    val animData by lazy { intent.getParcelableExtra<AnimData>("animData") }
    var firstFocus = true
    var anim:ValueAnimator? = null
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (firstFocus && hasFocus) {
           anim =  startShareAnim(csLayout, animData)
        }
        firstFocus = false
    }

    override fun finish() {
        if (anim!=null&& anim?.isRunning == true)
            return
        finishShareAnim(csLayout, animData, onAnimEnd = { superFinish() })
    }

    fun superFinish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}