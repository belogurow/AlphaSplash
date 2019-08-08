package ru.belogurow.unsplashclient.repository

import ru.belogurow.unsplashclient.api.UnsplashApiService
import ru.belogurow.unsplashclient.model.PhotoSort
import ru.belogurow.unsplashclient.model.PhotoSort.*

internal class UnsplashRepository(private val unsplashService: UnsplashApiService) {

    suspend fun photos(page: Int, perPage: Int, photoSort: PhotoSort) = when (photoSort) {
        FEATURED -> unsplashService.featuredPhotos(page.toString(), perPage.toString())
        OLDEST, LATEST, POPULAR -> unsplashService.photos(page.toString(), perPage.toString(), photoSort.toString())
    }

    suspend fun photoById(photoId: String) = unsplashService.photoById(photoId)

    suspend fun featuredPhotos(page: Int, perPage: Int) = unsplashService.featuredPhotos(page.toString(), perPage.toString())
}