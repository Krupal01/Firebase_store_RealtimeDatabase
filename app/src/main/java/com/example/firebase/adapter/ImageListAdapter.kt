package com.example.firebase.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.firebase.databinding.RowItemImageBinding
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.ArrayList

class ImageListAdapter : RecyclerView.Adapter<ImageListAdapter.MyViewHolder>() {

    private var ImageList : ArrayList<File> = ArrayList()
    fun setData(data : ArrayList<File>){
        ImageList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemImageBinding = RowItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemImageBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val bmp : Bitmap = BitmapFactory.decodeFile(ImageList.get(position).path)
        holder.binding.ItemImage.setImageBitmap(bmp)
        holder.binding.ItemTitle.text = ImageList.get(position).name
    }

    override fun getItemCount(): Int {
        return ImageList.size
    }

    class MyViewHolder(itemImageBinding: RowItemImageBinding) : RecyclerView.ViewHolder(itemImageBinding.root){
        val binding = itemImageBinding
    }
}