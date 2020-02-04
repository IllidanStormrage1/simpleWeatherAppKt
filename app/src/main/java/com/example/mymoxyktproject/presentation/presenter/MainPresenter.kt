package com.example.mymoxyktproject.presentation.presenter

import com.example.mymoxyktproject.data.response.CurrentWeatherEntity
import com.example.mymoxyktproject.domain.MainModel
import com.example.mymoxyktproject.presentation.ui.IMainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter


@InjectViewState
class MainPresenter : MvpPresenter<IMainView>() {
    override fun onFirstViewAttach() {
        viewState.getCurrentLocation()
    }

    fun loadData(lat: Float, long: Float) {
        CoroutineScope(Dispatchers.Main).launch {
            viewState.showProgressBar()
            when (val result = loadWeather(lat, long)) {
                null -> viewState.onError()
                else -> viewState.setData(result)
            }
            viewState.hideProgressBar()
            viewState.saveDataInPrefs(lat, long)
        }
    }

    private suspend fun loadWeather(lat: Float, long: Float): CurrentWeatherEntity? =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            MainModel.getDataFromApi(lat, long)
        }
}