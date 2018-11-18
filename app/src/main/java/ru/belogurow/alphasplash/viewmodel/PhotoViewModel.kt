package ru.belogurow.alphasplash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.belogurow.unsplashclient.UnsplashClient
import ru.belogurow.unsplashclient.model.PhotoResponse

class PhotoViewModel(private val unsplashClient: UnsplashClient) : ViewModel() {

    private var jobs = listOf<Job>()
    private var popularPhotos: LiveData<List<PhotoResponse>>? = null

    fun loadPopularPhotos(page: Int, perPage: Int): LiveData<List<PhotoResponse>>? {
        if (popularPhotos == null) {
            jobs += GlobalScope.launch {


                popularPhotos = MutableLiveData<List<PhotoResponse>>()
                val result = withContext(Dispatchers.IO) { unsplashClient.popularPhotos(page, perPage) }


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