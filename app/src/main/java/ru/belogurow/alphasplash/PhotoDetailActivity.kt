package ru.belogurow.alphasplash

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.belogurow.alphasplash.util.Const
import ru.belogurow.alphasplash.util.GlideApp
import ru.belogurow.unsplashclient.model.PhotoResponse

class PhotoDetailActivity : AppCompatActivity() {

    // FIXME При закрытии PhotoDetail в MainActivity заного подргружается список фотографий

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)

        val photoResponse: PhotoResponse? = intent.extras?.getParcelable(Const.EXTRA_PHOTO_RESPONSE_ITEM)

        val imagePhoto = findViewById<ImageView>(R.id.image_photo_detail).apply {
            GlideApp.with(this@PhotoDetailActivity)
                    .load(photoResponse?.urls?.regular)
                    .apply(RequestOptions()
                            .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(this)
        }
    }
}
