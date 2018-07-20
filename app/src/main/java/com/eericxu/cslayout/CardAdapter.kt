package com.eericxu.cslayout

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import com.eericxu.cslibrary.CSLayout
import com.eericxu.cslibrary.ScaleViewGesture
import com.eericxu.cslibrary.createAnimData
import java.security.MessageDigest

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
        val csLayout = holder.itemView as CSLayout
        val csHelper = csLayout.csHelper
        val res = imgs[position % 4]
        holder.setImageRes(R.id.iv_cover, res)

        val ivCover = holder.getView<ImageView>(R.id.iv_cover)
        ivCover?.apply {
//            val target = object : SimpleTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    ivCover.setImageBitmap(resource)
//                }
//            }
//            val options = RequestOptions().transform(object : BitmapTransformation() {
//                override fun updateDiskCacheKey(messageDigest: MessageDigest) {
//                }
//                override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
//                    val palette = Palette.from(toTransform).generate()
//                    val color = palette.getLightVibrantColor(Color.GRAY)
//                    val aColor = Color.argb(200, Color.red(color), Color.green(color), Color.blue(color))
//                    csHelper.mShadowColor = aColor
//                    csHelper.refresh()
//                    return toTransform
//                }
//            })
            Glide
                    .with(holder.itemView)
                    .load(res)
                    .into(ivCover)
        }
        holder.itemView.setOnClickListener({
            val ctx = it.context
            ctx.startActivity(
                    Intent(ctx, CSAty::class.java)
                            .putExtra("animData", createAnimData(csLayout, csHelper))
                            .putExtra("img", res)
            )
        })
//        ScaleViewGesture(holder.itemView.context).bindToView(csLayout, csLayout)?.onClick = {
//            val ctx = it.context
//
//            ctx.startActivity(
//                    Intent(ctx, CSAty::class.java)
//                            .putExtra("animData", createAnimData(csLayout, csHelper))
//                            .putExtra("img", res)
//            )
//        }

    }


}