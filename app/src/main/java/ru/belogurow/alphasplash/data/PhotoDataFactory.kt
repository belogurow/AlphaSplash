package ru.belogurow.alphasplash.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ru.belogurow.unsplashclient.model.PhotoResponse
import ru.belogurow.unsplashclient.model.PhotoSort

class PhotoDataFactory(
        private val photoSort: PhotoSort
) : DataSource.Factory<Int, PhotoResponse>() {

    private val sourceLiveData = MutableLiveData<PhotosDataSource>()

    override fun create(): DataSource<Int, PhotoResponse> {
        val dataSource = PhotosDataSource(photoSort)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}