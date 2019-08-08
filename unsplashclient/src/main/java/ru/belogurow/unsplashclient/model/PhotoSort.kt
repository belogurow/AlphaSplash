package ru.belogurow.unsplashclient.model

enum class PhotoSort(val sort: String) {
    LATEST("latest"),
    OLDEST("oldest"),
    POPULAR("popular"),
    FEATURED("featured")
}