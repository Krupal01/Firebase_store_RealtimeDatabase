package com.example.firebase.ui

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.firebase.R
import com.example.firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.teal_200)))

        binding.btnStudentInfo.setOnClickListener {
            changeScreen(StudentListActivity2())
        }
        binding.btnAddmode.setOnClickListener {
            changeScreen(AdModeActivity())
        }
        binding.btnNotification.setOnClickListener {
            changeScreen(UploadFileActivity())
        }
    }

    private fun changeScreen(activity: Activity) {

        val intent = Intent(this,activity::class.java)
        startActivity(intent)

    }
}