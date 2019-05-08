package ru.belogurow.unsplashclient.repository

import ru.belogurow.unsplashclient.api.UnsplashApiService
import ru.belogurow.unsplashclient.model.PhotoSort

internal class UnsplashRepository(private val unsplashService: UnsplashApiService) {

    fun photos(page: Int, perPage: Int, photoSort: PhotoSort) = unsplashService.photos(page.toString(), perPage.toString(), photoSort.sort)

    fun photoById(photoId: String) = unsplashService.photoById(photoId)

    fun featuredPhotos(page: Int, perPage: Int) = unsplashService.featuredPhotos(page.toString(), perPage.toString())
}