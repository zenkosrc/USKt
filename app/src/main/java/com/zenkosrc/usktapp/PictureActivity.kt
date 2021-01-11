package com.zenkosrc.usktapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_picture.*

class PictureActivity : AppCompatActivity() {

    companion object {
        const val ORIGINAL_SIZE_URL = "original_picture_url"
        const val REGULAR_SIZE_URL = "picture_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        loadPicture()
    }

    private fun loadPicture() {

        Glide.with(this)
            .load(intent.getStringExtra(REGULAR_SIZE_URL))
            .thumbnail(0.5f)
            .into(pictureImageView);
    }
}
