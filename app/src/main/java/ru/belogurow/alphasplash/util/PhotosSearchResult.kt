package ru.belogurow.alphasplash.util

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ru.belogurow.unsplashclient.model.PhotoResponse

data class PhotosSearchResult(
    val data: LiveData<PagedList<PhotoResponse>>,
    val networkErrors: LiveData<String>
)