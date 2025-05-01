package com.example.pinjambuku.network

import com.example.pinjambuku.model.BookModel
import com.google.gson.annotations.SerializedName

data class ProfileResponse (
    @field:SerializedName("error"      ) var error      : Boolean,
    @field:SerializedName("login"      ) var login      : Boolean,
    @field:SerializedName("message"    ) var message    : String,
    @field:SerializedName("profile" ) var profile : ProfileItem
)