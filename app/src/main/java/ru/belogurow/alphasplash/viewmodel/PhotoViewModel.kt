package ru.belogurow.alphasplash.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import ru.belogurow.unsplashclient.UnsplashClient
import ru.belogurow.unsplashclient.model.PhotoResponse

class PhotoViewModel(private val unsplashClient: UnsplashClient) : ViewModel() {

    private var jobs = listOf<Job>()
    private var popularPhotos: LiveData<List<PhotoResponse>>? = null

    fun loadPopularPhotos(page: Int, perPage: Int): LiveData<List<PhotoResponse>>? {
        if (popularPhotos == null) {
            jobs += launch {


                popularPhotos = MutableLiveData<List<PhotoResponse>>()
                val result = withContext(CommonPool) { unsplashClient.popularPhotos(page, perPage) }


//                if (result.isSuccessful && result.code() == 200) {
//                    popularPhotos?
//                    popularPhotos.value(result.body())
//                }
            }
        }

        return popularPhotos
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach {
            if (!it.isCancelled) {
                it.cancel()
            }
        }
    }
}