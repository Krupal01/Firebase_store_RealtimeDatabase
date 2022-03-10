package com.example.firebase.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import com.example.firebase.Utils
import com.example.firebase.databinding.ActivityUploadFileBinding
import java.io.*


class UploadFileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadFileBinding
    private lateinit var uri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectFile.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Select Image from here..."
                ),
                Utils.CHOOSE_FILE_REQUESTCODE
            )
        }

        binding.btnUploadFile.setOnClickListener {
            if (uri != null) {

                val fileName = uri.path?.substring(uri.path!!.lastIndexOf("/")+1)
                Utils.storageRef.child("images/"+fileName).putFile(uri)
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext,"upload sucess",Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(applicationContext,it.toString(),Toast.LENGTH_LONG).show()
                    }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Utils.CHOOSE_FILE_REQUESTCODE
            && resultCode == RESULT_OK
            && data != null
            && data.getData() != null) {

            uri = data.data!!
            binding.upFileName.text = uri.path
        }
    }


}