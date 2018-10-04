package ru.belogurow.alphasplash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.MemoryCategory
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.belogurow.alphasplash.ui.PhotosListFragment
import ru.belogurow.alphasplash.util.GlideApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlideApp.get(this).setMemoryCategory(MemoryCategory.HIGH);

        val photosListFragment = PhotosListFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_container, photosListFragment)
                .commit()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_navigation).apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Application.watchObject(this)
    }
}
