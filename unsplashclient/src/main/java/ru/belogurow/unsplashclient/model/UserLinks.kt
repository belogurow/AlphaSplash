package ru.belogurow.unsplashclient.model

import com.google.gson.annotations.SerializedName

data class UserLinks(

        @field:SerializedName("self")
        var self: String? = null,

        @field:SerializedName("html")
        var html: String? = null,

        @field:SerializedName("photos")
        var photos: String? = null,

        @field:SerializedName("likes")
        var likes: String? = null,

        @field:SerializedName("portfolio")
        var portfolio: String? = null,

        @field:SerializedName("following")
        var following: String? = null,

        @field:SerializedName("followers")
        var followers: String? = null
)

