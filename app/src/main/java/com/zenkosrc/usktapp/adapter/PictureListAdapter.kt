package com.zenkosrc.usktapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.zenkosrc.usktapp.R
import com.zenkosrc.usktapp.api.responses.ImageData
import kotlinx.android.synthetic.main.picture_list_item.view.*

class PictureListAdapter: RecyclerView.Adapter<PictureListAdapter.ViewHolder>() {

    private val TAG = PictureListAdapter::class.simpleName

    private var imageList = emptyList<ImageData>()

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

        val pictureImageView: ImageView = itemView.pictureImageView

        fun bind(imageData: ImageData){
            //TODO: Add Glige
        }

    }

    fun setData(newList: List<ImageData>){
        imageList = newList
        Log.d(TAG, "Image list size ${imageList.size}")
        notifyDataSetChanged()
    }

}