package com.eericxu.cslibrary

import android.os.Parcel
import android.os.Parcelable

class CSParms() : Parcelable {
    var mCornerLeftTop = 0f
    var mCornerTopRight = 0f
    var mCornerRightBottom = 0f
    var mCornerBottomLeft = 0f

    var mClipL = 0f
    var mClipT = 0f
    var mClipR = 0f
    var mClipB = 0f

    constructor(parcel: Parcel) : this() {
        mCornerLeftTop = parcel.readFloat()
        mCornerTopRight = parcel.readFloat()
        mCornerRightBottom = parcel.readFloat()
        mCornerBottomLeft = parcel.readFloat()
        mClipL = parcel.readFloat()
        mClipT = parcel.readFloat()
        mClipR = parcel.readFloat()
        mClipB = parcel.readFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(mCornerLeftTop)
        parcel.writeFloat(mCornerTopRight)
        parcel.writeFloat(mCornerRightBottom)
        parcel.writeFloat(mCornerBottomLeft)
        parcel.writeFloat(mClipL)
        parcel.writeFloat(mClipT)
        parcel.writeFloat(mClipR)
        parcel.writeFloat(mClipB)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CSParms> {
        override fun createFromParcel(parcel: Parcel): CSParms {
            return CSParms(parcel)
        }

        override fun newArray(size: Int): Array<CSParms?> {
            return arrayOfNulls(size)
        }
    }


}