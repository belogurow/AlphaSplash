package ru.belogurow.alphasplash.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.belogurow.alphasplash.Const
import ru.belogurow.alphasplash.R
import ru.belogurow.unsplashclient.UnsplashClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val unsplashClient = UnsplashClient(Const.UNSPLASH_KEY)

        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.activity_main_container, PhotosListFragment.newInstance())
        transaction.addToBackStack(null)

        transaction.commit()


    }
}
