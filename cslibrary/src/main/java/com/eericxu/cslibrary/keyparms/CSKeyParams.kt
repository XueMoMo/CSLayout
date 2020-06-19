package com.eericxu.cslibrary.keyparms

import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import com.eericxu.cslibrary.CSParms

class CSKeyParams:KeyParams{
    val csParms: CSParms
    constructor(key: String, rect: Rect,csParms: CSParms) : super(key, rect){
        this.csParms = csParms
    }
    constructor(parcel: Parcel) : super(parcel){
        csParms = parcel.readParcelable(CSParms::class.java.classLoader)!!
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        super.writeToParcel(dest, flags)
        dest?.writeParcelable(csParms,flags)
    }
    companion object CREATOR : Parcelable.Creator<CSKeyParams> {
        override fun createFromParcel(parcel: Parcel): CSKeyParams {
            return CSKeyParams(parcel)
        }

        override fun newArray(size: Int): Array<CSKeyParams?> {
            return arrayOfNulls(size)
        }
    }
}