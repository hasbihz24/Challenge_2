package com.example.challenge_2

import android.os.Parcel
import android.os.Parcelable
import java.net.URI

data class MyMenu(
    val gambar:Int,
    val nama: String?,
    val harga: String?,
    val lokasi: String?,
    val urlLokasi: String?,
    val deskripsi: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("urlLokasi")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(gambar)
        parcel.writeString(nama)
        parcel.writeString(harga)
        parcel.writeString(lokasi)
        parcel.writeString(deskripsi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyMenu> {
        override fun createFromParcel(parcel: Parcel): MyMenu {
            return MyMenu(parcel)
        }

        override fun newArray(size: Int): Array<MyMenu?> {
            return arrayOfNulls(size)
        }
    }
}