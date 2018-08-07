package ru.belogurow.unsplashclient

import okhttp3.OkHttpClient
import ru.belogurow.unsplashclient.api.OkHttpFactory
import ru.belogurow.unsplashclient.api.RetrofitFactory
import ru.belogurow.unsplashclient.api.UnsplashApiService
import ru.belogurow.unsplashclient.model.PhotoSort
import ru.belogurow.unsplashclient.repository.UnsplashRepository

// {@link https://unsplash.com/documentation}

class UnsplashClient(private val ACCESS_KEY: String) {

    private val httpClient: OkHttpClient.Builder
    private val unsplashService: UnsplashApiService
    private val unsplashRepository: UnsplashRepository

    init {
        val headers = mapOf("Authorization" to "Client-ID $ACCESS_KEY")

        httpClient = OkHttpFactory.createHttpClient(headers)
        unsplashService = RetrofitFactory.createService(httpClient, Const.BASE_URL)
        unsplashRepository = UnsplashRepository(unsplashService)
    }

    /**
     * Get a single page from the list of latest photos.
     *
     * @param page Page number to retrieve.
     * @param perPage Number of items per page.
     *
     * @return Object Call of PhotoResponse
     */
    suspend fun latestPhotos(page: Int, perPage: Int) = unsplashRepository.photos(page, perPage, PhotoSort.LATEST)

    /**
     * Get a single page from the list of oldest photos.
     *
     * @param page Page number to retrieve.
     * @param perPage Number of items per page.
     *
     * @return Object Call of PhotoResponse
     */
    suspend fun oldestPhotos(page: Int, perPage: Int) = unsplashRepository.photos(page, perPage, PhotoSort.OLDEST)

    /**
     * Get a single page from the list of popular photos.
     *
     * @param page Page number to retrieve.
     * @param perPage Number of items per page.
     *
     * @return Object Call of PhotoResponse
     */
    suspend fun popularPhotos(page: Int, perPage: Int) = unsplashRepository.photos(page, perPage, PhotoSort.POPULAR)

    /**
     *
     */
    suspend fun photoById(photoId: String) = unsplashRepository.photoById(photoId)
}
