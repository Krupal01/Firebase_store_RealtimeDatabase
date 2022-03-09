package com.example.firebase.model

import java.io.Serializable

data class StudentItem(
    val name : String,
    val phone:String
    ) : Serializable{

    constructor() : this("","")

}
