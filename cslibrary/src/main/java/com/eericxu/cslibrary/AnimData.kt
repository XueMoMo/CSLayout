package com.eericxu.cslibrary

import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable

data class AnimData(
        val rect: Rect,
        val csParms: CSParms
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Rect::class.java.classLoader),
            parcel.readParcelable(CSParms::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(rect, flags)
        parcel.writeParcelable(csParms, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimData> {
        override fun createFromParcel(parcel: Parcel): AnimData {
            return AnimData(parcel)
        }

        override fun newArray(size: Int): Array<AnimData?> {
            return arrayOfNulls(size)
        }
    }

}