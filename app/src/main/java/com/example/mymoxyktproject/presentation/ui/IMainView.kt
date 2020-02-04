package com.example.mymoxyktproject.presentation.ui

import com.example.mymoxyktproject.data.response.CurrentWeatherEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType


interface IMainView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showProgressBar()

    @StateStrategyType(SkipStrategy::class)
    fun hideProgressBar()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setData(data: CurrentWeatherEntity)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onError()

    @StateStrategyType(SkipStrategy::class)
    fun getCurrentLocation()

    @StateStrategyType(SkipStrategy::class)
    fun saveDataInPrefs(lat: Float, long: Float)

}