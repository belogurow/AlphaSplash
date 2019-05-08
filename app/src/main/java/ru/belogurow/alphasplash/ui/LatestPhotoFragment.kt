package ru.belogurow.alphasplash.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import ru.belogurow.alphasplash.PhotoDetailActivity
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.adapter.PhotoPagingAdapter
import ru.belogurow.alphasplash.ui.popular.PopularPhotoFragment
import ru.belogurow.alphasplash.util.*
import ru.belogurow.unsplashclient.model.PhotoResponse


class LatestPhotoFragment : Fragment() {

    private val TAG = PopularPhotoFragment::class.java.simpleName

    private lateinit var photosRecycler: RecyclerView
    private lateinit var photosAdapter: PhotoPagingAdapter
    private var page = 0
    private var perPage = 30
    private var listState: Parcelable? = null

    companion object {
        fun newInstance() = LatestPhotoFragment()
    }

    private lateinit var viewModel: LatestPhotoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.popular_photo_fragment, container, false)

        with(root) {

            val photoOnClickListener = object : OnItemClickListener<PhotoResponse> {
                override fun onItemClick(view: View, item: PhotoResponse) {
                    createTransitionToPhotoDetailScreen(view, item)
                }
            }

            photosRecycler = findViewById<RecyclerView>(R.id.photos_list_recycler).apply {
                val glideRequest = GlideApp.with(this@LatestPhotoFragment)
                val currentDisplay = CurrentDisplay(DisplayUtil.getScreenWidthInPx(requireContext()), DisplayUtil.dpToPx(450, requireContext()))

                photosAdapter = PhotoPagingAdapter(glideRequest, currentDisplay, photoOnClickListener)

                val photosPreloader: RecyclerViewPreloader<PhotoResponse> =
                        RecyclerViewPreloader(glideRequest, photosAdapter, photosAdapter, 6)

                layoutManager = LinearLayoutManager(root.context)
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
            val options = ActivityOptions.makeSceneTransitionAnimation(activity, view, view.transitionName)
            activity?.startActivity(intent, options.toBundle())
        } else {
            activity?.startActivity(intent)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LatestPhotoViewModel::class.java)

        viewModel.latestPhotosPaging.observe(this@LatestPhotoFragment, Observer {
            it?.let {
                photosAdapter.submitList(it)
            }
        })

        viewModel.loadPhotos()
    }
}
