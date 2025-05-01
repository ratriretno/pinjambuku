package com.example.pinjambuku.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel (
    @SerializedName("id"           ) var id          : String? = null,
    @SerializedName("name"         ) var name        : String? = null,
    @SerializedName("description"  ) var description : String? = null,
    @SerializedName("id_owner"     ) var idOwner     : String? = null,
    @SerializedName("available"    ) var available   : Boolean? = null,
    @SerializedName("photo_url"    ) var photoUrl    : String? = null,
    @SerializedName("date_created" ) var dateCreated : String? = null,
    @SerializedName("date_updated" ) var dateUpdated : String? = null,
    @SerializedName("year"         ) var year        : String? = null,
    @SerializedName("writer"       ) var writer      : String? = null,
    @SerializedName("publisher"    ) var publisher   : String? = null,
    @SerializedName("full_name"    ) var ownerName   : String? = null

) :Parcelable