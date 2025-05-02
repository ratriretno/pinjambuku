package com.example.pinjambuku.model

import com.google.gson.annotations.SerializedName

data class SignupItem (
    @SerializedName("email")  val email: String,
    @SerializedName("password")  val password: String,
    @SerializedName("fullname") val fullname : String,
    @SerializedName("username") val username : String,
    @SerializedName("city") val city : String,
    )