package com.digiart.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.android.material.imageview.ShapeableImageView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.digiart.R

class StaggeredGridAdapter(
    private val context: Context,
    private val imageUrls: List<String>,
    private val requestQueue: RequestQueue
) : RecyclerView.Adapter<StaggeredGridAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = imageUrls[position]

        // Load image using Glide for aspect ratio calculation
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<android.graphics.Bitmap>() {
                override fun onResourceReady(
                    resource: android.graphics.Bitmap,
                    transition: Transition<in android.graphics.Bitmap>?
                ) {
                    // Set the loaded image to the ImageView
                    holder.imageView.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {
                    // Implement if needed
                }
            })
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }
}