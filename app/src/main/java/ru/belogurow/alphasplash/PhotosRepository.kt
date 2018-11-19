package ru.belogurow.alphasplash

import ru.belogurow.unsplashclient.model.PhotoResponse

interface PhotosRepository {

    interface LoadPhotosCallback {
        fun onPhotosLoaded(photos: List<PhotoResponse>)

        fun onDataNotAvailable()
    }

    fun loadPopularPhotos(page: Int, perPage: Int, callback: LoadPhotosCallback)

    fun loadLatestPhotos(page: Int, perPage: Int, callback: LoadPhotosCallback)

    fun destroy()

}