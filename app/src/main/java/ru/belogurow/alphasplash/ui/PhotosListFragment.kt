package ru.belogurow.alphasplash.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import ru.belogurow.alphasplash.Const
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.adapter.PhotoAdapter
import ru.belogurow.unsplashclient.UnsplashClient

class PhotosListFragment : Fragment() {

    private lateinit var recyclerViewPhotos: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter

    companion object {
        @JvmStatic
        fun newInstance() = PhotosListFragment().apply {
            arguments = Bundle().apply {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photos_list, container, false);
        recyclerViewPhotos = view.findViewById(R.id.frag_photos_list_recycler)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerViewPhotos.layoutManager = LinearLayoutManager(requireContext())
        photoAdapter = PhotoAdapter(requireContext())
        recyclerViewPhotos.adapter = photoAdapter

        val unsplashClient = UnsplashClient(Const.UNSPLASH_KEY)


        launch(UI) {
            val result = withContext(CommonPool) { unsplashClient.popularPhotos(1, 10) }

            if (result.isSuccessful && result.code() == 200) {
                photoAdapter.photos = result.body()
            }
        }
    }


}
