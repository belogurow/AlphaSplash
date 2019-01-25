package ru.belogurow.alphasplash.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.util.CurrentDisplay
import ru.belogurow.alphasplash.util.OnItemClickListener
import ru.belogurow.unsplashclient.model.PhotoResponse

// TODO REFACTOR TO https://developer.android.com/topic/libraries/architecture/paging/

class PhotoAdapter(private var glideRequests: RequestManager,
                   private var currentDisplay: CurrentDisplay,
                   private var photoClickListener: OnItemClickListener<PhotoResponse>): RecyclerView.Adapter<PhotoAdapter.ViewHolder>(),
        ListPreloader.PreloadModelProvider<PhotoResponse>,
        ListPreloader.PreloadSizeProvider<PhotoResponse> {

    private var requestBuilder: RequestBuilder<Drawable> = glideRequests.asDrawable()

    private var photos: MutableList<PhotoResponse> = mutableListOf()

    fun addPhotos(newPhotos: List<PhotoResponse>) {
        val photoSize = photos.size
        photos.addAll(newPhotos)
        notifyItemRangeInserted(photoSize, newPhotos.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo2, parent, false))
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        var imageViewPhoto: ImageView = view.findViewById(R.id.item_photo_image2)

        fun bind(photo: PhotoResponse, glideRequests: RequestManager, photoClickListener: OnItemClickListener<PhotoResponse>) {
            glideRequests
                    //.asDrawable()
                    .load(photo.urls?.regular)
                    .transition(withCrossFade())
                    .apply(RequestOptions()
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(imageViewPhoto)

            imageViewPhoto.setOnClickListener { view ->  photoClickListener.onItemClick(view, photo)}
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position], glideRequests, photoClickListener)


    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)

        glideRequests.clear(holder.imageViewPhoto)
        holder.imageViewPhoto.setImageDrawable(null)
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemCount() = photos.size

    override fun getPreloadItems(position: Int): MutableList<PhotoResponse?> {
        return mutableListOf(photos[position])
    }

    override fun getPreloadRequestBuilder(item: PhotoResponse): RequestBuilder<*>? {
        return glideRequests
                .load(item)
                .apply(RequestOptions()
                        .override(currentDisplay.widthPx, currentDisplay.heightPx))
//                .transition(withCrossFade())

    }

    override fun getPreloadSize(item: PhotoResponse, adapterPosition: Int, perItemPosition: Int): IntArray? {
        return intArrayOf(currentDisplay.widthPx, currentDisplay.heightPx)
    }

}