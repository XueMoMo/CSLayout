package com.eericxu.cslayout

import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.Nullable
import android.support.annotation.RawRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class BaseHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    private val views: SparseArray<View> = SparseArray()
    fun <T : View> getView(@IdRes viewId: Int): T? {
        var view: View? = views.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            views.put(viewId, view)
        }
        if (view == null)
            return null
        return view as T
    }


    fun setText(@IdRes viewId: Int, str: CharSequence?) {
        getView<TextView>(viewId)?.text = str
    }

    fun setImageRes(@IdRes viewId: Int, @RawRes @DrawableRes @Nullable res: Int) {
        val view = getView<ImageView>(viewId) ?: return
        Glide.with(view).load(res).into(view)
    }
}