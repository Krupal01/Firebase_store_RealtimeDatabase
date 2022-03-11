package com.example.firebase.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.R
import com.example.firebase.Utils
import com.example.firebase.model.StudentItem
import com.example.firebase.ui.BottomSheetFragment
import com.example.firebase.ui.StudentListActivity2
import java.util.*

class StudentListAdapter(private var activity: Activity) : RecyclerView.Adapter<StudentListAdapter.MyViewHolder>() {

    var studentList : ArrayList<HashMap<String, StudentItem>> = ArrayList()

    fun setData(list : ArrayList<HashMap<String, StudentItem>>){
        this.studentList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_student_list,parent,false);
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hm = studentList.get(position)
        holder.bind(hm, this,activity)

    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val studentName : TextView = view.findViewById(R.id.ItemName)
        val studentPhone : TextView = view.findViewById(R.id.ItemPhone)
        val editBtn : ImageButton = view.findViewById(R.id.ItemEditBtn)
        val deleteBtn : ImageButton = view.findViewById(R.id.ItemDeleteBtn)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(map: HashMap<String, StudentItem>, adapter: StudentListAdapter, activity: Activity) {
            var key = map.keys.elementAt(0).toString()
            var studentItem = map.get(key)

            studentName.text = studentItem?.name
            studentPhone.text = studentItem?.phone

            editBtn.setOnClickListener {
                val bottomSheet = BottomSheetFragment(adapter)
                val bundle = Bundle()
                bundle.putSerializable(Utils.STUDENT_BUNDLE_TAG,studentItem)
                bundle.putString(Utils.STUDENT_KEY_TAG,key)
                bottomSheet.arguments = bundle
                bottomSheet.show((activity as StudentListActivity2).supportFragmentManager,
                    Utils.BOTTOM_SHEET_TAG
                )
            }
            deleteBtn.setOnClickListener {

                val alertDialogBuilder = AlertDialog.Builder(activity as StudentListActivity2)
                alertDialogBuilder.setTitle("Alert...")
                alertDialogBuilder.setMessage("Are you sure ?")
                alertDialogBuilder.setPositiveButton("yes"){dialog,which->
                    Utils.studentReference.child(key).removeValue()
                    adapter.setData(Utils.fetchData())
                    adapter.notifyDataSetChanged()
                }
                alertDialogBuilder.setNegativeButton("cancle"){dialog,which->
                    dialog.dismiss()
                }
                val dialog: AlertDialog = alertDialogBuilder.create()
                dialog.show()
            }
        }
    }
}