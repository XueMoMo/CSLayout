package com.eericxu.cslibrary.keyparms

import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable

open class KeyParams :Parcelable{
    val key: String
    var rect: Rect
    constructor(key: String = "key", rect: Rect) {
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

    constructor(parcel: Parcel) : this(parcel.readString()!!, parcel.readParcelable(Rect::class.java.classLoader)!!)

    companion object CREATOR : Parcelable.Creator<KeyParams> {
        override fun createFromParcel(parcel: Parcel): KeyParams {
            return KeyParams(parcel)
        }

        override fun newArray(size: Int): Array<KeyParams?> {
            return arrayOfNulls(size)
        }
    }
}