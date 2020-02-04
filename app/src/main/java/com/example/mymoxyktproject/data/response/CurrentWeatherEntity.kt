package com.example.mymoxyktproject.data.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWeatherEntity(
    @SerializedName("location")
    @Expose
    val location: LocationWeather,
    @SerializedName("current")
    @Expose
    val current: CurrentWeather
)