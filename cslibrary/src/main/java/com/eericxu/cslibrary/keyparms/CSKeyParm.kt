package com.eericxu.cslibrary.keyparms

import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import com.eericxu.cslibrary.CSParms

class CSKeyParm:KeyParm{
    val csParms: CSParms
    constructor(key: String, rect: Rect,csParms: CSParms) : super(key, rect){
        this.csParms = csParms
    }
    constructor(parcel: Parcel) : super(parcel){
        csParms = parcel.readParcelable(CSParms::class.java.classLoader)
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        super.writeToParcel(dest, flags)
        dest?.writeParcelable(csParms,flags)
    }
    companion object CREATOR : Parcelable.Creator<CSKeyParm> {
        override fun createFromParcel(parcel: Parcel): CSKeyParm {
            return CSKeyParm(parcel)
        }

        override fun newArray(size: Int): Array<CSKeyParm?> {
            return arrayOfNulls(size)
        }
    }
}