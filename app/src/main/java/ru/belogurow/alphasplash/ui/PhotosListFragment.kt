package ru.belogurow.alphasplash.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import ru.belogurow.alphasplash.Application
import ru.belogurow.alphasplash.CurrentDisplay
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.adapter.PhotoAdapter
import ru.belogurow.alphasplash.util.Const
import ru.belogurow.alphasplash.util.DisplayUtil
import ru.belogurow.unsplashclient.UnsplashClient
import ru.belogurow.unsplashclient.model.PhotoResponse


class PhotosListFragment() : Fragment() {

    private val TAG = PhotosListFragment::class.java.simpleName

    private lateinit var recyclerViewPhotos: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter
    private val unsplashClient = UnsplashClient(Const.UNSPLASH_KEY)

    private var page: Int = 1

//    companion object {
//        @JvmStatic
//        fun newInstance() = PhotosListFragment().apply {
//            arguments = Bundle().apply {}
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photos_list, container, false);

        initViews(view)

//        Application.watchObject(this)

        return view
    }

    private fun initViews(view: View) {
        recyclerViewPhotos = view.findViewById(R.id.frag_photos_list_recycler)

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewPhotos.layoutManager = layoutManager
        recyclerViewPhotos.setHasFixedSize(true)

        val glideRequest = Glide.with(this)
        val currentDisplay = CurrentDisplay(DisplayUtil.getScreenWidthInPx(requireContext()), DisplayUtil.dpToPx(250, requireContext()))

        photoAdapter = PhotoAdapter(glideRequest, currentDisplay)
        loadNewPhotos(page, 35)

        val photosPreloader: RecyclerViewPreloader<PhotoResponse> =
                RecyclerViewPreloader(Glide.with(this), photoAdapter, photoAdapter, 2)

        recyclerViewPhotos.addOnScrollListener(photosPreloader)
        recyclerViewPhotos.adapter = photoAdapter
    }

    private fun loadNewPhotos(page: Int, perPage: Int) {
        launch(UI) {
            val result = withContext(CommonPool) { unsplashClient.latestPhotos(page, perPage) }

            if (result.isSuccessful && result.code() == 200) {
                photoAdapter.photos = result.body()
            }
        }

        this.page += 1
    }

    override fun onDestroy() {
        super.onDestroy()
        Application.watchObject(this)
    }
}
