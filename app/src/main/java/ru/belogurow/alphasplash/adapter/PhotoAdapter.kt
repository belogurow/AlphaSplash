package ru.belogurow.alphasplash.adapter

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import ru.belogurow.alphasplash.CurrentDisplay
import ru.belogurow.alphasplash.R
import ru.belogurow.unsplashclient.model.PhotoResponse

class PhotoAdapter(private var glideRequests: RequestManager,
                   private var currentDisplay: CurrentDisplay): RecyclerView.Adapter<PhotoAdapter.ViewHolder>(),
        ListPreloader.PreloadModelProvider<PhotoResponse>,
        ListPreloader.PreloadSizeProvider<PhotoResponse> {

    private var requestBuilder: RequestBuilder<Drawable> = glideRequests.asDrawable()

    var photos: List<PhotoResponse>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

//    init {
////        setHasStableIds(true)
//
//    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var imageViewPhoto: ImageView = view.findViewById(R.id.item_photo_image2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo2, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoResponse = photos?.get(position)

        glideRequests
                .asDrawable()
                .load(photoResponse?.urls?.regular)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(holder.imageViewPhoto)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)

        glideRequests.clear(holder.imageViewPhoto)
        holder.imageViewPhoto.setImageDrawable(null)
    }

//    override fun getItemId(position: Int) = photos?.get(position)?.id?.toLong()!!

//    override fun getItemViewType(position: Int) = 0

    override fun getItemCount() = photos?.size ?: 0

    override fun getPreloadItems(position: Int): MutableList<PhotoResponse?> {
        return mutableListOf(photos?.get(position))
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