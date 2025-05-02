package com.example.pinjambuku.model

import com.google.gson.annotations.SerializedName

data class ProfileItem (
    @SerializedName("email")  val email: String,
    @SerializedName("password")  val password: String,
    @SerializedName("full_name") val fullname : String,
    @SerializedName("username") val username : String,
    @SerializedName("kota") val city : String,
)