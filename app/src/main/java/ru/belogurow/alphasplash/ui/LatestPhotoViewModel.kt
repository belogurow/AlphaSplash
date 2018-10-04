package ru.belogurow.alphasplash.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import ru.belogurow.alphasplash.util.Const
import ru.belogurow.unsplashclient.UnsplashClient
import ru.belogurow.unsplashclient.model.PhotoResponse
import kotlin.coroutines.experimental.CoroutineContext

class LatestPhotoViewModel : ViewModel(), CoroutineScope {

    private val latestPhotos: MutableLiveData<List<PhotoResponse>> by lazy { MutableLiveData<List<PhotoResponse>>() }

    private val unsplashClient = UnsplashClient(Const.UNSPLASH_KEY)
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun load(page: Int, perPage: Int) : MutableLiveData<List<PhotoResponse>> {
        this.launch(context = coroutineContext) {
            val photosDeferred = async { unsplashClient.latestPhotos(page, perPage) }


            val photosResult = photosDeferred.await()
            if (photosResult.isCompleted) {
                latestPhotos.value = photosResult.getCompleted().body()
            }
        }

        return latestPhotos
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
