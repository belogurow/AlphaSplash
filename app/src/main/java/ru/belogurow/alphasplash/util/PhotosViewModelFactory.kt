package ru.belogurow.alphasplash.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.belogurow.alphasplash.ui.latest.PhotoViewModel
import ru.belogurow.unsplashclient.model.PhotoSort

class PhotosViewModelFactory(
        private val photoSort: PhotoSort
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            return PhotoViewModel(photoSort) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}