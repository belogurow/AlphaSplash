package ru.belogurow.alphasplash.ui.latest

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import ru.belogurow.alphasplash.PhotoDetailActivity
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.adapter.PhotoAdapter
import ru.belogurow.alphasplash.architecture.PhotoContract
import ru.belogurow.alphasplash.util.*
import ru.belogurow.unsplashclient.model.PhotoResponse


class LatestPhotoFragment : Fragment(), PhotoContract.View {

    private val TAG = LatestPhotoFragment::class.java.simpleName

    override lateinit var presenter: PhotoContract.Presenter
    private lateinit var photosRecycler: RecyclerView
    private lateinit var photosAdapter: PhotoAdapter

    override var isActive: Boolean = false
        get() = isAdded

    companion object {
        fun newInstance() = LatestPhotoFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.popular_photo_fragment, container, false)

        with(root) {

            val photoOnClickListener = object : OnItemClickListener<PhotoResponse> {
                override fun onItemClick(view: View, item: PhotoResponse) {
                    createTransitionToPhotoDetailScreen(view, item)
                }
            }

            photosRecycler = findViewById<RecyclerView>(R.id.photos_list_recycler).apply {
                val glideRequest = GlideApp.with(this@LatestPhotoFragment)
                val currentDisplay = CurrentDisplay(DisplayUtil.getScreenWidthInPx(requireContext()), DisplayUtil.dpToPx(250, requireContext()))

                photosAdapter = PhotoAdapter(glideRequest, currentDisplay, photoOnClickListener)

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

    fun createTransitionToPhotoDetailScreen(view: View, photo: PhotoResponse) {
        val intent = Intent(activity, PhotoDetailActivity::class.java)
        intent.putExtra(Const.EXTRA_PHOTO_RESPONSE_ITEM, photo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(activity, view, activity?.getString(R.string.image_transition_to_photo_detail))
            activity?.startActivity(intent, options.toBundle())
        } else {
            activity?.startActivity(intent)
        }
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
