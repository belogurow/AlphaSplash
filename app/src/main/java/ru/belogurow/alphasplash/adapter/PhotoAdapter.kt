package ru.belogurow.alphasplash.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.belogurow.alphasplash.R
import ru.belogurow.unsplashclient.model.PhotoResponse

class PhotoAdapter(private val context: Context) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    var photos: List<PhotoResponse>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var imageViewPhoto: ImageView = view.findViewById(R.id.item_photo_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoResponse = photos?.get(position)
        Glide.with(context)
            .load(photoResponse?.urls?.full)
            .into(holder.imageViewPhoto)
    }


    override fun getItemCount() = photos?.size ?: 0

}