package ru.belogurow.alphasplash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.MemoryCategory
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.belogurow.alphasplash.ui.LatestPhotoFragment
import ru.belogurow.alphasplash.ui.popular.PopularPhotoFragment
import ru.belogurow.alphasplash.ui.popular.PopularPhotoPresenter
import ru.belogurow.alphasplash.util.GlideApp

class MainActivity : AppCompatActivity() {

    private lateinit var popularPhotoPresenter: PopularPhotoPresenter
//    private lateinit var latestPhotoPresenter: LatestPhotoPresenter

    private val popularPhotoFragment = PopularPhotoFragment.newInstance()
    private val latestPhotoFragment = LatestPhotoFragment()
    private var currentFragment: Fragment = popularPhotoFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlideApp.get(this@MainActivity).setMemoryCategory(MemoryCategory.HIGH);

        val photosRepository = PhotosRepositoryImpl()
        val popularPhotoFragment = PopularPhotoFragment.newInstance()
//        val latestPhotoFragment = LatestPhotoFragment.newInstance()


        // Create the presenter
        popularPhotoPresenter = PopularPhotoPresenter(30, photosRepository, popularPhotoFragment)
//        latestPhotoPresenter = LatestPhotoPresenter(30, photosRepository, latestPhotoFragment)

        // add first fragment
        addFragment(popularPhotoFragment)

        findViewById<BottomNavigationView>(R.id.main_bottom_navigation).apply {
            setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Application.watchObject(this)
    }

    private val navigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_popular -> {
//                toolbar.title = "Songs"
//                val popularPhotoFragment = PopularPhotoFragment.newInstance()
                openFragment(popularPhotoFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_latest -> {
//                toolbar.title = "Albums"
//                val latestPhotoFragment = LatestPhotoFragment.newInstance()
                openFragment(latestPhotoFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_favorites -> {
//                toolbar.title = "Artists"
//                val artistsFragment = ArtistsFragment.newInstance()
//                openFragment(artistsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(newFragment: Fragment) {
        if (currentFragment == newFragment) {
            return
        }

        addFragment(newFragment)

        supportFragmentManager.beginTransaction()
                .hide(currentFragment)
                .show(newFragment)
                .commit()

        currentFragment = newFragment
    }

    private fun addFragment(newFragment: Fragment) {
        if (!newFragment.isAdded) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.activity_main_container, newFragment, newFragment.tag)
                    .addToBackStack(newFragment.tag)
                    .commit()
        }
    }
}
