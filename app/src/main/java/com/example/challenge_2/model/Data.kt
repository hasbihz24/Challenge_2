package com.example.challenge_2.model

import android.os.Parcel
import android.os.Parcelable

data class Data(
    val alamatResto: String?,
    val createdAt: String?,
    val detail: String?,
    val harga: Int,
    val hargaFormat: String?,
    val id: Int,
    val imageUrl: String?,
    val nama: String?,
    val updatedAt: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(alamatResto)
        parcel.writeString(createdAt)
        parcel.writeString(detail)
        parcel.writeInt(harga)
        parcel.writeString(hargaFormat)
        parcel.writeInt(id)
        parcel.writeString(imageUrl)
        parcel.writeString(nama)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}