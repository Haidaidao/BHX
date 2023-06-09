package com.bhx.bhx.Model.Province;

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Contextual

@kotlinx.serialization.Serializable
data class Ward (
        @SerializedName("id")
        @Expose
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("district_id")
        val district_id: Int
)