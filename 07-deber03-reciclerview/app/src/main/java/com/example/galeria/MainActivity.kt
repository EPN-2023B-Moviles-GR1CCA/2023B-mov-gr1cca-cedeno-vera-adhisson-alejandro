package com.example.galeria

import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var imageRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var recentMediaRecycler: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recentMediaRecycler = findViewById(R.id.recent_media_recycler)
        imageRecycler = findViewById(R.id.image_recycler)
        progressBar = findViewById(R.id.recycler_progress)

        imageRecycler?.layoutManager = GridLayoutManager(this, 3) // tamaño de las imágenes
        imageRecycler?.setHasFixedSize(true)

        recentMediaRecycler?.layoutManager = GridLayoutManager(this, 2) // tamaño de las imágenes
        recentMediaRecycler?.setHasFixedSize(true)

        /*
        * Revisa si la app tiene permisos para leer el almacenamiento externo
        *     <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
        *      <uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
         */

        if (ContextCompat.checkSelfPermission(
                this,
                "android.permission.READ_MEDIA_IMAGES"
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                "android.permission.READ_MEDIA_VIDEO"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si no se conceden los permisos, solicítalos
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    "android.permission.READ_MEDIA_IMAGES",
                    "android.permission.READ_MEDIA_VIDEO"
                ),
                100
            )
        } else {
            // Si se conceden los permisos, carga las imágenes
            loadImages()
        }

        // Centrar el título de la ActionBar y mostrar el icono de la aplicación
        val actionBar: ActionBar? = supportActionBar
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.HORIZONTAL
        layout.gravity = Gravity.CENTER

        val imageView = ImageView(this)
        val drawable: Drawable? = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)
        imageView.setImageDrawable(drawable)
        imageView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val textView = TextView(this)
        textView.text = actionBar?.title
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        layout.addView(imageView)
        layout.addView(textView)

        val layoutParams = Toolbar.LayoutParams(
            Toolbar.LayoutParams.WRAP_CONTENT,
            Toolbar.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )

        actionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        actionBar?.customView = layout
        actionBar?.customView?.layoutParams = layoutParams

        // Cargar la imagen más reciente
        val screenshot = getLastScreenshot()
        val photoUri = getLastPhotoUri()
        val adapter = RecentMediaAdapter(this, screenshot, photoUri)
        recentMediaRecycler?.adapter = adapter

    } // onCreate


    private fun loadImages() {
        val cursorLoader = getAllImages()
        val cursor = cursorLoader.loadInBackground()

        val adapter = ImageAdapter(this, cursor)
        imageRecycler?.adapter = adapter
        progressBar?.visibility = View.GONE
    }

    private fun getAllImages(): CursorLoader {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA
        )
        val orderBy = MediaStore.Images.Media.DATE_TAKEN

        return CursorLoader(this, uri, projection, null, null, "$orderBy DESC")
    }

    private fun getLastScreenshot(): Bitmap? {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA
        )
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        val selection = "${MediaStore.Images.Media.DATA} LIKE ?"
        val selectionArgs = arrayOf("%Screenshots%")
        val cursor =
            contentResolver.query(uri, projection, selection, selectionArgs, "$orderBy DESC")

        var screenshot: Bitmap? = null
        if (cursor != null && cursor.moveToFirst()) {
            val screenshotId =
                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
            val screenshotUri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                screenshotId
            )
            screenshot = BitmapFactory.decodeStream(contentResolver.openInputStream(screenshotUri))
            cursor.close()
        }
        return screenshot
    }

    private fun getLastPhotoUri(): Uri? {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA
        )
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        val selection = "${MediaStore.Images.Media.DATA} NOT LIKE ?"
        val selectionArgs = arrayOf("%Screenshots%")
        val cursor =
            contentResolver.query(uri, projection, selection, selectionArgs, "$orderBy DESC")

        var photoUri: Uri? = null
        if (cursor != null && cursor.moveToFirst()) {
            val photoId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
            photoUri =
                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, photoId)
            cursor.close()
        }
        return photoUri
    }

}