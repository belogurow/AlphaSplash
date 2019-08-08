package ru.belogurow.alphasplash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.MemoryCategory
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.belogurow.alphasplash.ui.featured.FeaturedPhotoFragment
import ru.belogurow.alphasplash.ui.latest.LatestPhotoFragment
import ru.belogurow.alphasplash.util.GlideApp
import ru.belogurow.unsplashclient.model.PhotoSort

class MainActivity : AppCompatActivity() {

    private lateinit var latestPhotoFragment: Fragment
    private lateinit var featuredPhotoFragment: Fragment

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlideApp.get(this@MainActivity).setMemoryCategory(MemoryCategory.NORMAL);


        featuredPhotoFragment = FeaturedPhotoFragment.newInstance(PhotoSort.FEATURED)
        latestPhotoFragment = LatestPhotoFragment.newInstance()

        // add first fragment
        addFragment(featuredPhotoFragment)

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
            R.id.action_featured -> {
                openFragment(featuredPhotoFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_latest -> {
                openFragment(latestPhotoFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_favorites -> {
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

        currentFragment?.let {
            supportFragmentManager.beginTransaction()
                    .hide(it)
                    .show(newFragment)
                    .commit()
        }

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
