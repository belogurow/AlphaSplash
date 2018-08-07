package ru.belogurow.unsplashclient.repository

import ru.belogurow.unsplashclient.api.UnsplashApiService
import ru.belogurow.unsplashclient.model.PhotoSort

internal class UnsplashRepository(private val unsplashService: UnsplashApiService) {

    suspend fun photos(page: Int, perPage: Int, photoSort: PhotoSort) = unsplashService.photos(page.toString(), perPage.toString(), photoSort.sort).await()

    suspend fun photoById(photoId: String) = unsplashService.photoById(photoId).await()
}