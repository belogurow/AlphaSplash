package ru.belogurow.alphasplash.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import ru.belogurow.alphasplash.Application
import ru.belogurow.alphasplash.CurrentDisplay
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.adapter.PhotoAdapter
import ru.belogurow.alphasplash.util.Const
import ru.belogurow.alphasplash.util.DisplayUtil
import ru.belogurow.alphasplash.util.EndlessRecyclerViewScrollListener
import ru.belogurow.unsplashclient.UnsplashClient
import ru.belogurow.unsplashclient.model.PhotoResponse


class PhotosListFragment() : androidx.fragment.app.Fragment() {

    private val TAG = PhotosListFragment::class.java.simpleName

    private lateinit var recyclerViewPhotos: androidx.recyclerview.widget.RecyclerView
    private lateinit var photoAdapter: PhotoAdapter
    private val unsplashClient = UnsplashClient(Const.UNSPLASH_KEY)

    private var parentJob = Job()

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

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        layoutManager.orientation = androidx.recyclerview.widget.RecyclerView.VERTICAL
        recyclerViewPhotos.layoutManager = layoutManager
        recyclerViewPhotos.setHasFixedSize(true)

        val glideRequest = Glide.with(this)
        val currentDisplay = CurrentDisplay(DisplayUtil.getScreenWidthInPx(requireContext()), DisplayUtil.dpToPx(250, requireContext()))

        photoAdapter = PhotoAdapter(glideRequest, currentDisplay)
        loadNewPhotos(page, 30)

        val photosPreloader: RecyclerViewPreloader<PhotoResponse> =
                RecyclerViewPreloader(glideRequest, photoAdapter, photoAdapter, 6)

        val endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                Log.d("page", page.toString())
                Log.d("totalItemsCount", totalItemsCount.toString())
                loadNewPhotos(page, 30)
            }

        }

        recyclerViewPhotos.addOnScrollListener(photosPreloader)
        recyclerViewPhotos.addOnScrollListener(endlessRecyclerViewScrollListener)
        recyclerViewPhotos.adapter = photoAdapter
    }

    private fun loadNewPhotos(page: Int, perPage: Int) {
        launch(UI, parent = parentJob) {
            val result = withContext(CommonPool) { unsplashClient.latestPhotos(page, perPage) }

            if (result.isSuccessful && result.code() == 200 && result.body() != null) {
//                photoAdapter.photos = result.body()
                Toast.makeText(context, "loaded", Toast.LENGTH_SHORT).show()
                photoAdapter.addPhotos(result.body()!!)
            }
        }

        this.page += 1
    }

    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancel()
        Application.watchObject(this)
    }
}
