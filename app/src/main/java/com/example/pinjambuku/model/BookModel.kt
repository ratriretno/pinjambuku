package com.example.pinjambuku.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel (
    @SerializedName("id"           ) var id          : String? = "",
    @SerializedName("name"         ) var name        : String? = "",
    @SerializedName("description"  ) var description : String? = "",
    @SerializedName("id_owner"     ) var idOwner     : String? = "",
    @SerializedName("available"    ) var available   : Boolean = true,
    @SerializedName("photo_url"    ) var photoUrl    : String? = "",
    @SerializedName("date_created" ) var dateCreated : String? = "",
    @SerializedName("date_updated" ) var dateUpdated : String? = "",
    @SerializedName("year"         ) var year        : String? = "",
    @SerializedName("writer"       ) var writer      : String? = "",
    @SerializedName("publisher"    ) var publisher   : String? = "",
    @SerializedName("full_name"    ) var ownerName   : String? = ""

) :Parcelable