package com.example.firebase.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.adapter.StudentListAdapter
import com.example.firebase.Utils
import com.example.firebase.databinding.ActivityStudentList2Binding

class StudentListActivity2 : AppCompatActivity() {

    private lateinit var adapter: StudentListAdapter
    private lateinit var binding: ActivityStudentList2Binding
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentList2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = StudentListAdapter(this)

        var datalist = Utils.fatchData()
        Log.i("krupal",datalist.toString())
        adapter.setData(datalist)
        Log.i("krupal","adapter data set ")
        binding.rcvStudernList.layoutManager = LinearLayoutManager(applicationContext)
        binding.rcvStudernList.adapter = adapter
        Log.i("krupal","adapter set ")

        binding.FabAddStudent.setOnClickListener {
            val bottomSheet = BottomSheetFragment(adapter)
            bottomSheet.show(supportFragmentManager,Utils.BOTTOM_SHEET_TAG)
        }
    }
}