package ru.belogurow.alphasplash.util

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import ru.belogurow.unsplashclient.model.PhotoResponse
import java.io.InputStream

class PhotoModelLoader(
        concreteLoader: ModelLoader<GlideUrl, InputStream>?,
        modelCache: ModelCache<PhotoResponse, GlideUrl>
) :
        BaseGlideUrlLoader<PhotoResponse>(concreteLoader, modelCache) {


    override fun getUrl(model: PhotoResponse?, width: Int, height: Int, options: Options): String {
        return model?.urls?.regular ?: ""
    }

    override fun handles(model: PhotoResponse): Boolean {
        return true
    }

    class Factory : ModelLoaderFactory<PhotoResponse, InputStream> {

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<PhotoResponse, InputStream> {
            val urlLoader = multiFactory.build(GlideUrl::class.java, InputStream::class.java)
            return PhotoModelLoader(urlLoader, photoCache)
        }

        override fun teardown() {}
    }

    companion object {
        private val photoCache = ModelCache<PhotoResponse, GlideUrl>(300)
    }
}
