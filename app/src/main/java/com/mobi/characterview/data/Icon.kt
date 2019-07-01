package com.mobi.characterview.data

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

class Icon protected constructor(`in`: Parcel) : Parcelable {
    @SerializedName("Height")
    var height: String? = null

    @SerializedName("Width")
    var width: String? = null

    @SerializedName("URL")
    var url: String? = null

    init {
        height = `in`.readString()
        width = `in`.readString()
        url = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(height)
        dest.writeString(width)
        dest.writeString(url)
    }

    companion object {
        @SuppressLint("ParcelCreator")
        val CREATOR: Parcelable.Creator<Icon> = object : Parcelable.Creator<Icon> {
            override fun createFromParcel(`in`: Parcel): Icon {
                return Icon(`in`)
            }

            override fun newArray(size: Int): Array<Icon?> {
                return arrayOfNulls(size)
            }
        }
    }
}