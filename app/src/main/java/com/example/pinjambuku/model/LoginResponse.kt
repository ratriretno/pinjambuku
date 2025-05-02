package com.example.pinjambuku.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @field:SerializedName("error"      ) var error      : Boolean,
    @field:SerializedName("login"      ) var login      : Boolean,
    @field:SerializedName("message"    ) var message    : String,
    @field:SerializedName("id"    ) var idUser    : String

)