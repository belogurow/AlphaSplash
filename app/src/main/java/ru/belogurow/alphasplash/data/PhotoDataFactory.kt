package ru.belogurow.alphasplash.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ru.belogurow.unsplashclient.model.PhotoResponse

class PhotoDataFactory : DataSource.Factory<Int, PhotoResponse>() {

    private val sourceLiveData = MutableLiveData<PhotosDataSource>()

    override fun create(): DataSource<Int, PhotoResponse> {
        val dataSource = PhotosDataSource()
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}