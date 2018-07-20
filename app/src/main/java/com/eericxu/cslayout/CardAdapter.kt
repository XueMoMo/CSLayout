package com.eericxu.cslayout

import android.content.Intent
import android.support.v4.app.SupportActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eericxu.cslibrary.CSLayout
import com.eericxu.cslibrary.ScaleViewGesture
import com.eericxu.cslibrary.createAnimData

class CardAdapter : RecyclerView.Adapter<BaseHolder>() {

    val imgs = arrayOf(R.mipmap.img_1, R.mipmap.img_2, R.mipmap.img_3, R.mipmap.img_4)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return BaseHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_one
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val res = imgs[position % 4]
        holder.setImageRes(R.id.iv_cover, res)
        val csLayout = holder.itemView as CSLayout
        val csHelper = csLayout.csHelper
//        holder.itemView.setOnClickListener {
//
//
//        }
        ScaleViewGesture(holder.itemView.context).bindToView(csLayout, csLayout)?.onClick = {
            val ctx = it.context

            ctx.startActivity(
                    Intent(ctx, CSAty::class.java)
                            .putExtra("animData", createAnimData(csLayout, csHelper))
                            .putExtra("img", res)
            )
//                csLayout.visibility = View.INVISIBLE
//                (ctx as BaseAty).lifecycle.addObserver(object : LifeObserver() {
//                    var isFirst = true
//                    override fun onResume() {
//                        if (isFirst) {
//                            isFirst = false
//                            return
//                        }
//                        csLayout.visibility = View.VISIBLE
//                    }
//                })

        }

    }


}