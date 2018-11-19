package ru.belogurow.alphasplash.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.belogurow.alphasplash.R
import ru.belogurow.unsplashclient.model.PhotoResponse

class PhotoTextAdapter: RecyclerView.Adapter<PhotoTextAdapter.ViewHolder>() {

    private var photos: MutableList<PhotoResponse> = mutableListOf()

    fun addPhotos(newPhotos: List<PhotoResponse>) {
        val photoSize = photos.size
        photos.addAll(newPhotos)
        notifyItemRangeInserted(photoSize, newPhotos.size)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textPhoto = itemView.findViewById<TextView>(R.id.photo_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoTextAdapter.ViewHolder {
        return PhotoTextAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo_text, parent, false))
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotoTextAdapter.ViewHolder, position: Int) {
        val photoResponse = photos[position]

        holder.textPhoto.text = "${photoResponse.user} ${photoResponse.description}"
    }
}