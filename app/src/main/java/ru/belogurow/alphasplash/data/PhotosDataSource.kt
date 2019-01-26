package ru.belogurow.alphasplash.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*
import ru.belogurow.alphasplash.util.Const
import ru.belogurow.unsplashclient.UnsplashClient
import ru.belogurow.unsplashclient.model.PhotoResponse
import kotlin.coroutines.CoroutineContext

class PhotosDataSource(
//        photoSort: PhotoSort
) : PageKeyedDataSource<Int, PhotoResponse>(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val unsplashClient = UnsplashClient(Const.UNSPLASH_KEY)

    private var lastRequestedPage = 0

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PhotoResponse>) {
        this.launch {
            val photosResponse = withContext(Dispatchers.IO) { unsplashClient.latestPhotos(
                    page = 1,
                    perPage = params.requestedLoadSize) }.await()

            if (photosResponse.isSuccessful) {
                callback.onResult(photosResponse.body()?.toMutableList()!!, null, 2)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoResponse>) {
        this.launch {
            val photosResponse = withContext(Dispatchers.IO) { unsplashClient.latestPhotos(
                    page = params.key,
                    perPage = params.requestedLoadSize) }.await()

            if (photosResponse.isSuccessful) {
                callback.onResult(photosResponse.body()?.toMutableList()!!, params.key + 1)
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoResponse>) {
//        this.launch {
//            val photosResponse = withContext(Dispatchers.IO) { unsplashClient.latestPhotos(
//                    page = params.key,
//                    perPage = params.requestedLoadSize) }.await()
//
//            if (photosResponse.isSuccessful) {
//                val pageKey = if (params.key == 0) {
//                    null
//                } else {
//                    params.key + 1
//                }
//
//                callback.onResult(photosResponse.body()?.toMutableList()!!, pageKey)
//            }
//        }
    }

}