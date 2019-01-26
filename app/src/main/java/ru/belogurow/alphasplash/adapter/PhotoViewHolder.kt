package ru.belogurow.alphasplash.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.util.OnItemClickListener
import ru.belogurow.unsplashclient.model.PhotoResponse

class PhotoViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_photo2, parent, false)) {

    var imageViewPhoto = itemView.findViewById<ImageView>(R.id.item_photo_image2)
//    private val textView = itemView.findViewById<TextView>(R.id.photo_text)
    var photoResponse : PhotoResponse? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bind(photo: PhotoResponse?, glideRequests: RequestManager, photoClickListener: OnItemClickListener<PhotoResponse>) {
        this.photoResponse = photo

        photo?.let {
            glideRequests
                    //.asDrawable()
                    .load(photo.urls?.regular)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions()
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(imageViewPhoto)

            imageViewPhoto.setOnClickListener { view ->  photoClickListener.onItemClick(view, photo)}
        }
    }

}