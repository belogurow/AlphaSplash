package ru.belogurow.alphasplash.ui.latest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.belogurow.alphasplash.data.PhotoDataFactory
import ru.belogurow.unsplashclient.model.PhotoResponse
import ru.belogurow.unsplashclient.model.PhotoSort
import kotlin.coroutines.CoroutineContext

class PhotoViewModel(
        private val photoSort: PhotoSort
) : ViewModel(), CoroutineScope {

//    val latestPhotos: MutableLiveData<List<PhotoResponse>> by lazy { MutableLiveData<List<PhotoResponse>>() }
//    private val page: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
//    private val perPage = 30
//    private val unsplashClient = UnsplashClient(Const.UNSPLASH_KEY)

    private val job = Job()

//    private val pagedListConfig = PagedList.Config.Builder()
//            .setEnablePlaceholders(true)
//            .setInitialLoadSizeHint(perPage * 3)
//            .setPageSize(perPage)
//            .build()

//    private val photosResult: MutableLiveData<PhotosSearchResult> by lazy { MutableLiveData<PhotosSearchResult>() }

    private val searchQuery = MutableLiveData<String>()
    var latestPhotosPaging: LiveData<PagedList<PhotoResponse>> = Transformations.switchMap(searchQuery) {
        loadNextPhotos()
    }

//    init {
//        page.value = 0
//    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

//    fun load(page: Int, perPage: Int) : MutableLiveData<List<PhotoResponse>> {
//        this.launch(context = coroutineContext) {
//            val photosDeferred = withContext(Dispatchers.IO) { unsplashClient.latestPhotos(page, perPage) }
//
//            val photosResult = photosDeferred.await()
//            if (photosResult.isSuccessful) {
//                latestPhotos.value = photosResult.body()
//            }
//
//            increasePage()
//        }
//
//        return latestPhotos
//    }

    fun loadNextPhotos(): LiveData<PagedList<PhotoResponse>> {
        val dataFactory = PhotoDataFactory(photoSort)

        val config = PagedList.Config.Builder()
                .setPrefetchDistance(10)
                .setInitialLoadSizeHint(30)
                .setPageSize(30)
                .build()

        val data = LivePagedListBuilder(dataFactory, config)
                .setInitialLoadKey(1)
                .build()

        return data
    }

    fun loadPhotos() {
        searchQuery.value = "latest"
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}
