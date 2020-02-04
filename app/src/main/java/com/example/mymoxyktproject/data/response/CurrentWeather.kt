package com.example.mymoxyktproject.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("temperature")
    @Expose
    val temperature: Int,
    @Expose
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("weather_descriptions")
    @Expose
    val weatherDescriptions: List<String>,
    @SerializedName("weather_icons")
    @Expose
    val weatherIcons: List<String>,
    @SerializedName("observation_time")
    @Expose
    val observationTime: String,
    @SerializedName("humidity")
    @Expose
    val humidity: Int,
    @SerializedName("wind_speed")
    @Expose
    val windSpeed: Int,
    @SerializedName("cloudcover")
    @Expose
    val cloudCover: Int
)