package ru.belogurow.alphasplash.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.util.OnItemClickListener
import ru.belogurow.unsplashclient.model.PhotoResponse


class PhotoViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_photo2, parent, false)) {

    val imageViewPhoto = itemView.findViewById<ImageView>(R.id.item_photo_image2)
    var photoResponse: PhotoResponse? = null

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
                            .signature(ObjectKey(photoResponse?.id!!))
                            .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(imageViewPhoto)

            imageViewPhoto.setOnClickListener { view -> photoClickListener.onItemClick(view, photo) }
        }
    }

}
