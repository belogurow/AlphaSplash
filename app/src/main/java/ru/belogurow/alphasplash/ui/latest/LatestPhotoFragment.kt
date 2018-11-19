package ru.belogurow.alphasplash.ui.latest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.adapter.PhotoTextAdapter
import ru.belogurow.alphasplash.architecture.PhotoContract
import ru.belogurow.unsplashclient.model.PhotoResponse


class LatestPhotoFragment : Fragment(), PhotoContract.View {

    private val TAG = LatestPhotoFragment::class.java.simpleName

    override lateinit var presenter: PhotoContract.Presenter
    private lateinit var photosRecycler: RecyclerView
    private var photosAdapter = PhotoTextAdapter()

    override var isActive: Boolean = false
        get() = isAdded

    companion object {
        fun newInstance() = LatestPhotoFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.popular_photo_fragment, container, false)

        with(root) {

            photosRecycler = findViewById<RecyclerView>(R.id.photos_list_recycler).apply {
                adapter = photosAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }


        }

        return root
    }


    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun addPhotosToList(newPhotos: List<PhotoResponse>) {
        photosAdapter.addPhotos(newPhotos)
//        Toast.makeText(context, "Loaded", Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        Toast.makeText(context, "Cannot load data", Toast.LENGTH_SHORT).show()
    }

}
