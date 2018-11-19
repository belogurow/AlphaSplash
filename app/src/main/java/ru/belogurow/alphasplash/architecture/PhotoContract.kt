package ru.belogurow.alphasplash.architecture

import ru.belogurow.unsplashclient.model.PhotoResponse

interface PhotoContract {

    interface View: BaseView<Presenter> {
        var isActive: Boolean

        fun addPhotosToList(newPhotos: List<PhotoResponse>)

        fun showError()
    }

    interface Presenter: BasePresenter {
        fun loadNext(page: Int, perPage: Int)
    }
}