package com.example.firebase.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.adapter.StudentListAdapter
import com.example.firebase.Utils
import com.example.firebase.databinding.ActivityStudentList2Binding
import com.example.firebase.model.StudentItem
import kotlinx.coroutines.runBlocking
import java.util.ArrayList
import java.util.HashMap

class StudentListActivity2 : AppCompatActivity() {

    private lateinit var adapter: StudentListAdapter
    private lateinit var binding: ActivityStudentList2Binding
    private var datalist : ArrayList<HashMap<String, StudentItem>> = ArrayList()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentList2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = StudentListAdapter(this)

        datalist = Utils.fatchData()
        adapter.setData(datalist)
        Log.i("krupal",datalist.toString())
        Log.i("krupal","adapter data set ")
        binding.rcvStudernList.layoutManager = LinearLayoutManager(applicationContext)
        binding.rcvStudernList.setHasFixedSize(true)
        binding.rcvStudernList.adapter = adapter
        Log.i("krupal","adapter set ")

        binding.FabAddStudent.setOnClickListener {
            val bottomSheet = BottomSheetFragment(adapter)
            bottomSheet.show(supportFragmentManager,Utils.BOTTOM_SHEET_TAG)
        }
    }
}