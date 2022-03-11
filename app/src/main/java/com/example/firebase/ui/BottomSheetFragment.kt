package com.example.firebase.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebase.adapter.StudentListAdapter
import com.example.firebase.Utils
import com.example.firebase.databinding.FragmentBottomSheetBinding
import com.example.firebase.model.StudentItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BottomSheetFragment(private var adapter: StudentListAdapter) : BottomSheetDialogFragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentBottomSheetBinding
    private var IsAdding = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reference = Utils.studentReference

        var argStudentItem = arguments?.getSerializable(Utils.STUDENT_BUNDLE_TAG)
        var argStudentKey : String? = arguments?.getString(Utils.STUDENT_KEY_TAG)
        if (argStudentKey != null) {
            Log.i("Krupal",argStudentKey)
        }else{
            Log.i("Krupal","key null")
        }
        if (argStudentItem != null && argStudentKey!=null ){
            initScreen(argStudentItem as StudentItem)
        }

        binding.BSFAddBtn.setOnClickListener {
            if (validated()){
                if (IsAdding) {
                    reference.push().setValue(
                        StudentItem(
                            binding.BSFStudentname.text.toString(),
                            binding.BSFStudentPhone.text.toString()
                        )
                    )
                }else{
                    if (argStudentKey != null) {
                        reference.child(argStudentKey).setValue(
                            StudentItem(
                                binding.BSFStudentname.text.toString(),
                                binding.BSFStudentPhone.text.toString()
                            )
                        )
                        IsAdding = true

                    }
                }
                adapter.setData(Utils.fetchData())
                adapter.notifyDataSetChanged()
                dismiss()
            }
        }

        binding.BSFCancleBtn.setOnClickListener {
            this.dismiss()
        }


    }

    private fun validated() : Boolean {
        if (binding.BSFStudentname.text.toString().equals("")){
            binding.BSFStudentname.error = "enter valid name"
            return false
        }
        if (binding.BSFStudentPhone.text.toString().equals("") || binding.BSFStudentPhone.text.toString().length!=10){
            binding.BSFStudentPhone.error = "enter valid number"
            return false
        }
        return true
    }

    private fun initScreen(arg: StudentItem) {
        IsAdding = false
        binding.BSFHeadText.text = "Update Student Info :"
        binding.BSFStudentname.text = getEditable(arg.name)
        binding.BSFStudentPhone.text = getEditable(arg.phone)
        binding.BSFAddBtn.text = "Update"
    }

    private fun getEditable(i: String): Editable = Editable.Factory.getInstance().newEditable(i)

}