package com.eericxu.cslayout

//import android.support.v7.graphics.Palette
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eericxu.cslibrary.CSLayout
import com.eericxu.cslibrary.ScaleViewGesture
import com.eericxu.cslibrary.createIntentDef

class CardAdapter : RecyclerView.Adapter<BaseHolder>() {

    val imgs = arrayOf(R.mipmap.img_1, R.mipmap.img_2, R.mipmap.img_3, R.mipmap.img_4)
    var clickView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return BaseHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 3 < 1) R.layout.item_one else R.layout.item_two
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val csLayout = holder.itemView as CSLayout
        val res = imgs[position % 4]
        holder.setImageRes(R.id.iv_cover, res)
        val tvTit = holder.getView<TextView>(R.id.tv_title)
        val ivCover = holder.getView<ImageView>(R.id.iv_cover)
        ivCover?.apply { Glide.with(holder.itemView).load(res).into(this) }
        holder.itemView.setOnClickListener {
            val ctx = it.context
            clickView = csLayout
            val intent = Intent(ctx, CSAty::class.java).putExtra("img", res)
            ctx.startActivity(createIntentDef(intent, csLayout,
                    "imgView" to ivCover as View,
                    "tvTit" to tvTit as View
            ))
        }


//        ScaleViewGesture(holder.itemView.context)
//                .setCustumScale(0.95f)
//                .bindToView(csLayout, csLayout)
//                ?.onClick = {
//                    val ctx = it.context
//                    clickView = csLayout
//                    val intent = Intent(ctx, CSAty::class.java).putExtra("img", res)
//                    ctx.startActivity(createIntentDef(intent, csLayout,
//                            "imgView" to ivCover as View,
//                            "tvTit" to tvTit as View
//                    ))
//                }

    }

//    fun onPause() {
//        clickView?.postDelayed({ clickView?.visibility = View.INVISIBLE }, 100)
//    }
//
//    fun onResume() {
//        clickView?.visibility = View.VISIBLE
//        clickView = null
//    }


}