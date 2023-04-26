package com.example.contactsfirebasek

data class User(
    var name: String = "",
    var email: String = "",
    var phone: String = ""
) {
    constructor() : this("", "", "") // Default constructor required for Firebase
}