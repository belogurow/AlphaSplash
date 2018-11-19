package ru.belogurow.alphasplash

import kotlinx.coroutines.*
import ru.belogurow.alphasplash.util.Const
import ru.belogurow.unsplashclient.UnsplashClient
import kotlin.coroutines.CoroutineContext

class PhotosRepositoryImpl : PhotosRepository {

    private val unsplashClient = UnsplashClient(Const.UNSPLASH_KEY)
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main


    override fun loadPopularPhotos(page: Int, perPage: Int, callback: PhotosRepository.LoadPhotosCallback) {
        GlobalScope.launch(coroutineContext) {
            val photosDeferred = withContext(Dispatchers.IO) { unsplashClient.popularPhotos(page, perPage) }

            val photosResult = photosDeferred.await()

            if (photosResult.isSuccessful && photosResult.code() == 200 && photosResult.body() != null) {
                callback.onPhotosLoaded(photosResult.body()!!)
            } else {
                callback.onDataNotAvailable()
            }
        }
    }

    override fun loadLatestPhotos(page: Int, perPage: Int, callback: PhotosRepository.LoadPhotosCallback) {
        GlobalScope.launch(coroutineContext) {
            val photosDeferred = withContext(Dispatchers.IO) { unsplashClient.latestPhotos(page, perPage) }

            val photosResult = photosDeferred.await()

            if (photosResult.isSuccessful && photosResult.code() == 200 && photosResult.body() != null) {
                callback.onPhotosLoaded(photosResult.body()!!)
            } else {
                callback.onDataNotAvailable()
            }
        }
    }

    override fun destroy() {
        // TODO
        // Как здесь завершить parentJob?
        // писать корутину в presenter или использовать viewmodel?
        parentJob.cancel()
    }

}