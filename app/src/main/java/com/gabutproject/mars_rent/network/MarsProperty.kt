package com.gabutproject.mars_rent.network

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class MarsProperty(
    val id: String,
    @Json(name = "img_src")
    val imgSrcUrl: String,
    val type: String,
    val price: Double
) : Parcelable {
    val rental get() = type == "rent"

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(imgSrcUrl)
        parcel.writeString(type)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MarsProperty> {
        override fun createFromParcel(parcel: Parcel): MarsProperty {
            return MarsProperty(parcel)
        }

        override fun newArray(size: Int): Array<MarsProperty?> {
            return arrayOfNulls(size)
        }
    }
}