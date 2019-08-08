package ru.belogurow.unsplashclient.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoResponse(

        @field:SerializedName("current_user_collections")
        val currentUserCollections: List<String>? = null,

        @field:SerializedName("color")
        val color: String? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("sponsored")
        val sponsored: Boolean? = null,

        @field:SerializedName("liked_by_user")
        val likedByUser: Boolean? = null,

        @field:SerializedName("urls")
        val urls: Urls? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("width")
        val width: Int? = null,

        @field:SerializedName("userLinks")
        val links: Links? = null,

        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("categories")
        val categories: List<String>? = null,

        @field:SerializedName("user")
        val user: User? = null,

        @field:SerializedName("slug")
        val slug: String? = null,

        @field:SerializedName("height")
        val height: Int? = null,

        @field:SerializedName("likes")
        val likes: Int? = null
) : Parcelable
