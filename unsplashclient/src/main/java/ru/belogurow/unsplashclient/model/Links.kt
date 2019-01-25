package ru.belogurow.unsplashclient.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Links(

        @field:SerializedName("self")
        var self: String? = null,

        @field:SerializedName("html")
        var html: String? = null,

        @field:SerializedName("download")
        var download: String? = null,

        @field:SerializedName("download_location")
        var downloadLocation: String? = null
) : Parcelable

