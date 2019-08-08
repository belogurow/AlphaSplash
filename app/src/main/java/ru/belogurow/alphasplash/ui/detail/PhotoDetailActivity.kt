package ru.belogurow.alphasplash.ui.detail

import android.Manifest
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.google.android.material.button.MaterialButton
import ru.belogurow.alphasplash.R
import ru.belogurow.alphasplash.util.Const
import ru.belogurow.alphasplash.util.CurrentDisplay
import ru.belogurow.alphasplash.util.DisplayUtil
import ru.belogurow.alphasplash.util.GlideApp
import ru.belogurow.unsplashclient.model.PhotoResponse
import java.io.ByteArrayOutputStream


class PhotoDetailActivity : AppCompatActivity() {

    private val TAG = PhotoDetailActivity::class.java.simpleName
    private val RECORD_REQUEST_CODE = 103

    private lateinit var imagePhoto: ImageView
    private lateinit var glideRequests: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)

        val photoResponse: PhotoResponse? = intent.extras?.getParcelable(Const.EXTRA_PHOTO_RESPONSE_ITEM)

        loadPhoto(photoResponse)

        val buttonSetWallpaper = findViewById<MaterialButton>(R.id.button_set_wallpaper).apply {
            setOnClickListener {
                if (setupPermissions()) {
                    val wallpaperManager = WallpaperManager.getInstance(this@PhotoDetailActivity)

                    val bitmap = (imagePhoto.drawable as BitmapDrawable).bitmap
//                    wallpaperManager.setBitmap(bitmap)
                    val intent = wallpaperManager.getCropAndSetWallpaperIntent(getImageUri(this@PhotoDetailActivity, bitmap, "image"))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }

        findViewById<TextView>(R.id.photo_detail_text).apply {
            text = photoResponse?.user?.username
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadPhoto(photoResponse: PhotoResponse?) {
        val currentDisplay = CurrentDisplay(DisplayUtil.getScreenWidthInPx(applicationContext), DisplayUtil.dpToPx(450, applicationContext))

        glideRequests = GlideApp.with(this@PhotoDetailActivity)

        imagePhoto = findViewById<ImageView>(R.id.image_photo_detail).apply {
            glideRequests
                    .load(photoResponse?.urls?.regular)
                    .apply(RequestOptions()
                            .override(currentDisplay.widthPx, currentDisplay.heightPx)
                            .signature(ObjectKey(photoResponse?.id!!))
                            .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(this)
        }
    }


    private fun getImageUri(context: Context, imageBitmap: Bitmap, fileName: String): Uri {
        val bytes = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val uri = MediaStore.Images.Media.insertImage(context.contentResolver, imageBitmap, fileName, null)
        return Uri.parse(uri)
    }

    private fun setupPermissions(): Boolean {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission to record denied", Toast.LENGTH_LONG).show()
            makeRequest()
            return false
        }

        return true
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                RECORD_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.d(TAG, "Permission has been denied by user")
                } else {
                    Log.d(TAG, "Permission has been granted by user")
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finishAfterTransition()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()

        glideRequests.clear(imagePhoto)
        imagePhoto.setImageDrawable(null)
    }
}
