package com.example.mymoxyktproject.data

import com.example.mymoxyktproject.data.response.CurrentWeatherEntity
import com.example.mymoxyktproject.domain.consts.ApplicationConstants.API_KEY
import com.example.mymoxyktproject.domain.consts.ApplicationConstants.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApixuWeatherService {

    @GET("current")
    suspend fun getCurrentWeather(
        @Query("access_key") accessKey: String = API_KEY, @Query("query") query: String
    ): Response<CurrentWeatherEntity>

    companion object {
        operator fun invoke(): ApixuWeatherService = retrofitCall

        private val retrofitCall = with(Retrofit.Builder()) {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }.create(ApixuWeatherService::class.java)
    }
}
