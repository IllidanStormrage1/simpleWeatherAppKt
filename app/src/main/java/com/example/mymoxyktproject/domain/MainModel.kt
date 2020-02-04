package com.example.mymoxyktproject.domain

import com.example.mymoxyktproject.data.ApixuWeatherService
import com.example.mymoxyktproject.data.response.CurrentWeatherEntity


object MainModel {
    suspend fun getDataFromApi(lat: Float, long: Float): CurrentWeatherEntity? =
        try {
            ApixuWeatherService().getCurrentWeather(query = "$lat,$long").body()
        } catch (t: Exception) {
            null
        }

}