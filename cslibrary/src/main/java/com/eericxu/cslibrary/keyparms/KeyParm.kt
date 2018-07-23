package com.eericxu.cslibrary.keyparms

import android.animation.Animator
import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable

open class KeyParm :Parcelable{
    val key: String
    var rect: Rect
    constructor(key: String, rect: Rect) {
        this.key = key
        this.rect = rect
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(key)
        dest?.writeParcelable(rect, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Rect::class.java.classLoader))

    companion object CREATOR : Parcelable.Creator<KeyParm> {
        override fun createFromParcel(parcel: Parcel): KeyParm {
            return KeyParm(parcel)
        }

        override fun newArray(size: Int): Array<KeyParm?> {
            return arrayOfNulls(size)
        }
    }
}