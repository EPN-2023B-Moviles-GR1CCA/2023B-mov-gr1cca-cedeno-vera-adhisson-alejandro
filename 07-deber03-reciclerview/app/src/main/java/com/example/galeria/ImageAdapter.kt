package com.example.galeria

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

class ImageAdapter(private val context: Context, private var cursor: Cursor?) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView? = null

        init {
            image = itemView.findViewById(R.id.row_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.row_custom_recycler_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        if (cursor?.moveToPosition(position) == true) {
            val imagePath =
                cursor?.getString(cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) ?: 0)
            Glide.with(context)
                .load(File(imagePath))
                .apply(RequestOptions().centerCrop())
                .into(holder.image!!)

            holder.image!!.setOnClickListener {
                val intent = Intent(context, ImageFullActivity::class.java)
                intent.putExtra("imagePath", imagePath)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    fun swapCursor(newCursor: Cursor?) {
        cursor?.close()
        cursor = newCursor
        notifyDataSetChanged()
    }
}