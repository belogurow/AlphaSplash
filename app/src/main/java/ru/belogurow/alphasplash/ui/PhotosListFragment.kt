package ru.belogurow.alphasplash.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import kotlinx.coroutines.*
import ru.belogurow.alphasplash.Application
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.adapter.PhotoAdapter
import ru.belogurow.alphasplash.util.*
import ru.belogurow.unsplashclient.UnsplashClient
import ru.belogurow.unsplashclient.model.PhotoResponse


class PhotosListFragment : androidx.fragment.app.Fragment() {

    private val TAG = PhotosListFragment::class.java.simpleName

    private lateinit var recyclerViewPhotos: androidx.recyclerview.widget.RecyclerView
    private lateinit var photoAdapter: PhotoAdapter
    private val unsplashClient = UnsplashClient(Const.UNSPLASH_KEY)

    private var parentJob = Dispatchers.Main + Job()

    private var page = 1
    private var perPage = 30

    private lateinit var viewModel: LatestPhotoViewModel

//    companion object {
//        @JvmStatic
//        fun newInstance() = PhotosListFragment().apply {
//            arguments = Bundle().apply {}
//        }
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LatestPhotoViewModel::class.java)

        viewModel.load(page, perPage).observe(this, Observer {
            photoAdapter.addPhotos(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photos_list, container, false)

        initViews(view)

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


        val photoOnClickListener = object : OnItemClickListener<PhotoResponse> {
            override fun onItemClick(view: View, item: PhotoResponse) {
//                createTransitionToPhotoDetailScreen(view, item)
            }
        }

        photoAdapter = PhotoAdapter(glideRequest, currentDisplay, photoOnClickListener)
        loadNewPhotos(page, perPage)

        val photosPreloader: RecyclerViewPreloader<PhotoResponse> =
                RecyclerViewPreloader(glideRequest, photoAdapter, photoAdapter, 6)

        val endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                Log.d("page", page.toString())
                Log.d("totalItemsCount", totalItemsCount.toString())
                loadNewPhotos(page, perPage)
            }

        }

        recyclerViewPhotos.addOnScrollListener(photosPreloader)
        recyclerViewPhotos.addOnScrollListener(endlessRecyclerViewScrollListener)
        recyclerViewPhotos.adapter = photoAdapter
    }

    private fun loadNewPhotos(page: Int, perPage: Int) {
        GlobalScope.launch(parentJob) {
            val photosDeferred = withContext(Dispatchers.IO) { unsplashClient.latestPhotos(page, perPage) }

            val photosResult = photosDeferred.await()

            if (photosResult.isSuccessful && photosResult.code() == 200 && photosResult.body() != null) {
//                photoAdapter.photos = result.body()
//                Toast.makeText(context, "loaded", Toast.LENGTH_SHORT).show()
                photoAdapter.addPhotos(photosResult.body()!!)
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
