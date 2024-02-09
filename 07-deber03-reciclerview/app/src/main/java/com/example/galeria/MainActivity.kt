package com.example.galeria

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var imageRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var allPictures: ArrayList<Image>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageRecycler = findViewById(R.id.image_recycler)
        progressBar = findViewById(R.id.recycler_progress)

        imageRecycler?.layoutManager = GridLayoutManager(this, 3)
        imageRecycler?.setHasFixedSize(true)

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


        allPictures = ArrayList()

        if (allPictures!!.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            // Obtiene todas las imágenes del almacenamiento externo
            allPictures = getAllImages()
            // Muestra las imágenes en el RecyclerView
            imageRecycler?.adapter = ImageAdapter(this, allPictures!!)
            progressBar?.visibility = View.GONE
        }

    } // onCreate


    private fun loadImages() {
        allPictures = ArrayList()

        if (allPictures!!.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            // Obtiene todas las imágenes del almacenamiento externo
            allPictures = getAllImages()
            // Muestra las imágenes en el RecyclerView
            imageRecycler?.adapter = ImageAdapter(this, allPictures!!)
            progressBar?.visibility = View.GONE
        }
    }

    private fun getAllImages(): ArrayList<Image> {
        val images = ArrayList<Image>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA
        )
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        val cursor = contentResolver.query(uri, projection, null, null, "$orderBy DESC")

        try {
            if (cursor != null) {
                do {
                    cursor.moveToFirst()
                    val id =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                    val path =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    images.add(Image(path, name))

                } while (cursor.moveToNext())
                cursor.close()

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return images
    }

}