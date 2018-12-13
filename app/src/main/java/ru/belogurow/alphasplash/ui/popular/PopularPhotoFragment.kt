package ru.belogurow.alphasplash.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.adapter.PhotoAdapter
import ru.belogurow.alphasplash.architecture.PhotoContract
import ru.belogurow.alphasplash.util.CurrentDisplay
import ru.belogurow.alphasplash.util.DisplayUtil
import ru.belogurow.alphasplash.util.GlideApp
import ru.belogurow.unsplashclient.model.PhotoResponse


class PopularPhotoFragment : Fragment(), PhotoContract.View {

    private val TAG = PopularPhotoFragment::class.java.simpleName

    override lateinit var presenter: PhotoContract.Presenter
    private lateinit var photosRecycler: RecyclerView
    private lateinit var photosAdapter: PhotoAdapter

    override var isActive: Boolean = false
        get() = isAdded

    companion object {
        fun newInstance() = PopularPhotoFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.popular_photo_fragment, container, false)

        with(root) {

            photosRecycler = findViewById<RecyclerView>(R.id.photos_list_recycler).apply {
                val glideRequest = GlideApp.with(this@PopularPhotoFragment)
                val currentDisplay = CurrentDisplay(DisplayUtil.getScreenWidthInPx(requireContext()), DisplayUtil.dpToPx(250, requireContext()))

                photosAdapter = PhotoAdapter(glideRequest, currentDisplay)

                val photosPreloader: RecyclerViewPreloader<PhotoResponse> =
                        RecyclerViewPreloader(glideRequest, photosAdapter, photosAdapter, 6)

                layoutManager = LinearLayoutManager(context)
                adapter = photosAdapter
                setHasFixedSize(true)
                addOnScrollListener(photosPreloader)
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
