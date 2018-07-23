package com.eericxu.cslayout

//import android.support.v7.graphics.Palette
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.eericxu.cslibrary.CSLayout
import com.eericxu.cslibrary.ScaleViewGesture
import com.eericxu.cslibrary.createIntent
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
        val csHelper = csLayout.csHelper
        val res = imgs[position % 4]
        holder.setImageRes(R.id.iv_cover, res)

        val ivCover = holder.getView<ImageView>(R.id.iv_cover)
        ivCover?.apply {
            Glide
                    .with(holder.itemView)
                    .load(res)
                    .into(ivCover)
        }
//        holder.itemView.setOnClickListener({
//            val ctx = it.context
//            clickView = csLayout
//            ctx.startActivity(
//                    Intent(ctx, CSAty::class.java)
//                            .putExtra("animData", createAnimData(csLayout, csHelper))
//                            .putExtra("img", res)
//            )
//        })
        ScaleViewGesture(holder.itemView.context).bindToView(csLayout, csLayout)?.onClick = {
            val ctx = it.context
            clickView = csLayout
            val intent = Intent(ctx, CSAty::class.java).putExtra("img", res)
            ctx.startActivity(createIntentDef(intent, "csLayout" to csLayout as View, "imgView" to ivCover as View))
        }

    }

    fun onPause() {
        clickView?.postDelayed({ clickView?.visibility = View.INVISIBLE }, 100)
    }

    fun onResume() {
        clickView?.visibility = View.VISIBLE
        clickView = null
    }


}