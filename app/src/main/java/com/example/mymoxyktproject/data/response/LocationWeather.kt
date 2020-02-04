package com.example.mymoxyktproject.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationWeather(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("country")
    @Expose
    val country: String
)