package com.eericxu.cslayout

import android.content.Context
import android.util.TypedValue

class DisplayUtils {
    companion object {
        var dp1 = 1
        fun init(ctx: Context) {
            dp1 = dip2px(ctx, 1f)
        }

        fun dip2px(context: Context?, dp: Float): Int {
            return if (context == null) {
                -1
            } else TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    dp, context.resources.displayMetrics).toInt()
        }


        fun dpInt(dp: Float): Int {
            return (dp1 * dp).toInt()
        }

    }
}