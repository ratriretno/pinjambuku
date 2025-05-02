package com.example.pinjambuku.model

import com.google.gson.annotations.SerializedName

data class LoginItem(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)