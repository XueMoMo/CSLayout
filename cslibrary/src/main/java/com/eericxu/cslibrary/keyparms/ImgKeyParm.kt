package com.eericxu.cslibrary.keyparms

import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable

class ImgKeyParm:KeyParm,Parcelable{

    constructor(key: String, rect: Rect) : super(key, rect)
    constructor(parcel: Parcel) : super(parcel)

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        super.writeToParcel(dest, flags)
    }
    companion object CREATOR : Parcelable.Creator<ImgKeyParm> {
        override fun createFromParcel(parcel: Parcel): ImgKeyParm {
            return ImgKeyParm(parcel)
        }

        override fun newArray(size: Int): Array<ImgKeyParm?> {
            return arrayOfNulls(size)
        }
    }
}