package com.eericxu.cslibrary.keyparms

import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable

class ImgKeyParams:KeyParams,Parcelable{

    constructor(key: String, rect: Rect) : super(key, rect)
    constructor(parcel: Parcel) : super(parcel)

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        super.writeToParcel(dest, flags)
    }
    companion object CREATOR : Parcelable.Creator<ImgKeyParams> {
        override fun createFromParcel(parcel: Parcel): ImgKeyParams {
            return ImgKeyParams(parcel)
        }

        override fun newArray(size: Int): Array<ImgKeyParams?> {
            return arrayOfNulls(size)
        }
    }
}