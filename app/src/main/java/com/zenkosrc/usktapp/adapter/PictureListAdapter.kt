package com.zenkosrc.usktapp.adapter

import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zenkosrc.usktapp.R
import com.zenkosrc.usktapp.api.responses.ImageData
import kotlinx.android.synthetic.main.picture_list_item.view.*

class PictureListAdapter: RecyclerView.Adapter<PictureListAdapter.ViewHolder>() {

    private val TAG = PictureListAdapter::class.simpleName

    private var imageList = emptyList<ImageData>()
    var onItemClick: ((ImageData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.picture_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(imageList[adapterPosition])
            }
        }

        fun bind(imageData: ImageData){

            Glide.with(itemView.context)
                .load(imageData.getSmall())
                .thumbnail(0.3f)
                .placeholder(ColorDrawable(ContextCompat.getColor(itemView.context, android.R.color.transparent)))
                .into(itemView.pictureImageView);
        }
    }

    fun setData(newList: List<ImageData>){
        imageList = newList
        Log.d(TAG, "Image list size ${imageList.size}")
        notifyDataSetChanged()
    }
}