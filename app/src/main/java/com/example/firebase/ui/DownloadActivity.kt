package com.example.firebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.Utils
import com.example.firebase.adapter.ImageListAdapter
import com.example.firebase.databinding.ActivityDownloadBinding
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.ArrayList

class DownloadActivity : AppCompatActivity() {

    private lateinit var fileList : ArrayList<File>
    private lateinit var adapter: ImageListAdapter
    private lateinit var binding : ActivityDownloadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fileList = ArrayList()
        adapter = ImageListAdapter()
        adapter.setData(fileList)

        loadData(Utils.storageRef)
        binding.rcvImageList.layoutManager = LinearLayoutManager(applicationContext)
        binding.rcvImageList.adapter = adapter

    }

    private fun loadData(storageRef: StorageReference) {
        storageRef.listAll().addOnSuccessListener {
            it.items.forEach {
                val file = File.createTempFile(it.name,"jpg")
                it.getFile(file).addOnSuccessListener {
                    fileList.add(file)
                    adapter.notifyDataSetChanged()
                }.addOnFailureListener{
                    Toast.makeText(applicationContext, "item Exception$it",Toast.LENGTH_LONG).show()
                }
            }
            it.prefixes.forEach {
                loadData(it)
            }
        }.addOnFailureListener {
            Toast.makeText(applicationContext,it.toString(),Toast.LENGTH_LONG).show()
        }
    }
}