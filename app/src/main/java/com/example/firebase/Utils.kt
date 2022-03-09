package com.example.firebase

import android.util.Log
import com.example.firebase.model.StudentItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.ArrayList
import java.util.HashMap

class Utils {

    companion object{
        val BOTTOM_SHEET_TAG = "BOTTOM_SHEET_TAG"
        val STUDENT_BUNDLE_TAG = "STUDENT_BUNDLE_TAG"
        val ERROR_TAG = "ERROR_TAG"
        val STUDENT_KEY_TAG = "STUDENT_KEY_TAG"
        var CHOOSE_FILE_REQUESTCODE = 22

        val storageRef : StorageReference = Firebase.storage.reference

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val studentReference = firebaseDatabase.getReference("Students")


        fun fatchData(): ArrayList<HashMap<String, StudentItem>> {
            var studentList : ArrayList<HashMap<String,StudentItem>> = ArrayList()
            studentReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var iterator : Iterator<DataSnapshot>  = snapshot.children.iterator()
                    while (iterator.hasNext()){
                        var child = iterator.next()
                        var key = child.key
                        var value = child.getValue(StudentItem::class.java)
                        var hm = HashMap<String,StudentItem>()
                        hm.put(key!!,value!!)
                        studentList.add(hm)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.i(ERROR_TAG,error.toString())
                }
            })
            return studentList
        }
    }



}