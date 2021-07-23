package com.digitalsamurai.a3205teamtest.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataUserRepo(


    @Expose
    @SerializedName("id")
    var id : Int,

    @Expose
    @SerializedName("name")
    var name : String,

    @Expose
    @SerializedName("full_name")
    var fullName : String
)

