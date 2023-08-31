package com.example.easycommerce.model.data

//dataclass for user
data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    var imagePath: String = ""
){
    //empty constructor for firebase
    constructor(): this("","","","")

}
