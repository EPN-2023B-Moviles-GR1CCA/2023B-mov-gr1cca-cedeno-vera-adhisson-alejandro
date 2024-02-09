package com.example.galeria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide

class ImageFullActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_full)

        val imagePath = intent.getStringExtra("imagePath")
        val imageName = intent.getStringExtra("imageName")

        supportActionBar?.title = imageName

        Glide.with(this)
            .load(imagePath)
            .into(findViewById(R.id.image_view))


    }
}