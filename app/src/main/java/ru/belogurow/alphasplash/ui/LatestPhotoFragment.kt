package ru.belogurow.alphasplash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ru.belogurow.alphasplash.R

class LatestPhotoFragment : Fragment() {

    companion object {
        fun newInstance() = LatestPhotoFragment()
    }

    private lateinit var viewModel: LatestPhotoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.latest_photo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LatestPhotoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
