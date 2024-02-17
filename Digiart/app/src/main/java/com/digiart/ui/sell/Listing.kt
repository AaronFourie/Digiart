package com.digiart.ui.sell

import android.os.Parcel
import android.os.Parcelable

data class Listing(
    val title: String,
    val description: String,
    val tags: String,
    val category: String,
    val price: Double,
    val listing_media: String // This will store the media URI
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(tags)
        parcel.writeString(category)
        parcel.writeDouble(price)
        parcel.writeString(listing_media)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Listing> {
        override fun createFromParcel(parcel: Parcel): Listing {
            return Listing(parcel)
        }

        override fun newArray(size: Int): Array<Listing?> {
            return arrayOfNulls(size)
        }
    }
}