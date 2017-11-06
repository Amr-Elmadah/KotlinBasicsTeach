package com.amr.kotlinteach.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose


/**
 * Created by Amr El-Madah on 11/6/2017.
 */
class Fact : Parcelable {

    @Expose
    var fact: String = ""

    constructor() {}

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.fact)
    }

    protected constructor(ins: Parcel) {
        this.fact = ins.readString()
    }

    companion object {

        val CREATOR: Parcelable.Creator<Fact> = object : Parcelable.Creator<Fact> {
            override fun createFromParcel(source: Parcel): Fact {
                return Fact(source)
            }

            override fun newArray(size: Int): Array<Fact?> {
                return arrayOfNulls(size)
            }
        }
    }
}
