package com.mobi.characterview.data

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

class Topic protected constructor(`in`: Parcel) : Parcelable {

    @SerializedName("Text")
    var text: String? = null

    @SerializedName("Icon")
    var icon: Icon? = null

    @SerializedName("FirstURL")
    var firstURL: String? = null

    @SerializedName("Result")
    var result: String? = null

    init {
        text = `in`.readString()
        firstURL = `in`.readString()
        result = `in`.readString()
        icon = `in`.readParcelable(Icon::class.java.classLoader)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(text)
        dest.writeString(firstURL)
        dest.writeString(result)
        dest.writeParcelable(icon, flags)
    }

    companion object {

        @SuppressLint("ParcelCreator")
        val CREATOR: Parcelable.Creator<Topic> = object : Parcelable.Creator<Topic> {
            override fun createFromParcel(`in`: Parcel): Topic {
                return Topic(`in`)
            }

            override fun newArray(size: Int): Array<Topic?> {
                return arrayOfNulls(size)
            }
        }
    }
}