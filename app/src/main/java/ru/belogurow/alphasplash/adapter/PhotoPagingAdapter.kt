package ru.belogurow.alphasplash.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import ru.belogurow.alphasplash.util.CurrentDisplay
import ru.belogurow.alphasplash.util.OnItemClickListener
import ru.belogurow.unsplashclient.model.PhotoResponse

class PhotoPagingAdapter(private var glideRequests: RequestManager,
                         private var currentDisplay: CurrentDisplay,
                         private var photoClickListener: OnItemClickListener<PhotoResponse>) :
        PagedListAdapter<PhotoResponse, PhotoViewHolder>(diffCallback),
        ListPreloader.PreloadModelProvider<PhotoResponse>,
        ListPreloader.PreloadSizeProvider<PhotoResponse> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder = PhotoViewHolder(parent)

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), glideRequests, photoClickListener)
    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         */
        private val diffCallback = object : DiffUtil.ItemCallback<PhotoResponse>() {
            override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse) =
                    oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse) =
                    oldItem == newItem

        }
    }

    override fun onViewRecycled(holder: PhotoViewHolder) {
        super.onViewRecycled(holder)

        glideRequests.clear(holder.imageViewPhoto)
        holder.imageViewPhoto.setImageDrawable(null)
    }

    override fun getPreloadItems(position: Int): MutableList<PhotoResponse?> {
        return mutableListOf(getItem(position))
    }

    override fun getPreloadRequestBuilder(item: PhotoResponse): RequestBuilder<*>? {
        return glideRequests
                .load(item)
//                .apply(RequestOptions()
//                        .override(currentDisplay.widthPx, currentDisplay.heightPx))
//                .transition(withCrossFade())

    }

    override fun getPreloadSize(item: PhotoResponse, adapterPosition: Int, perItemPosition: Int): IntArray? {
        return intArrayOf(currentDisplay.widthPx, currentDisplay.heightPx)
    }
}
