package ru.belogurow.alphasplash.ui.popular

import ru.belogurow.alphasplash.PhotosRepository
import ru.belogurow.alphasplash.architecture.PhotoContract
import ru.belogurow.unsplashclient.model.PhotoResponse

class PopularPhotoPresenter(
        private val perPage: Int,
        val photosRepository: PhotosRepository,
        val photosView: PhotoContract.View
) : PhotoContract.Presenter{

    private var page = 0

    init {
        photosView.presenter = this
    }

    override fun start() {
        loadNext(page, perPage)
    }

    override fun loadNext(page: Int, perPage: Int) {
        photosRepository.loadPopularPhotos(page, perPage, object: PhotosRepository.LoadPhotosCallback {
            override fun onPhotosLoaded(photos: List<PhotoResponse>) {
                if (photosView.isActive) {
                    photosView.addPhotosToList(photos)
                }
            }

            override fun onDataNotAvailable() {
                if (photosView.isActive) {
                    photosView.showError()
                }
            }
        })
    }

    override fun destroy() {
        photosRepository.destroy()
    }
}