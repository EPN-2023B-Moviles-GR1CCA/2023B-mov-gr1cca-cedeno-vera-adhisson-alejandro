package com.example.galeria

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecentMediaAdapter(
    private val context: Context,
    private val screenshot: Bitmap?,
    private val cameraUri: Uri?
) :
    RecyclerView.Adapter<RecentMediaAdapter.RecentMediaViewHolder>() {

    inner class RecentMediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.recent_image)
        var imageName: TextView = itemView.findViewById(R.id.recent_image_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMediaViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.recent_custom_recycler_item, parent, false)
        return RecentMediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentMediaViewHolder, position: Int) {
        if (position == 0) {
            holder.image.setImageBitmap(screenshot)
            holder.imageName.text = "Screenshots"
        } else if (position == 1) {
            holder.image.setImageURI(cameraUri)
            holder.imageName.text = "Cámara"
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}