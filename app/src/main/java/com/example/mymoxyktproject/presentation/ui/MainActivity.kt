package com.example.mymoxyktproject.presentation.ui

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.mymoxyktproject.R
import com.example.mymoxyktproject.data.response.CurrentWeatherEntity
import com.example.mymoxyktproject.domain.consts.ApplicationConstants.PREF_LAT
import com.example.mymoxyktproject.domain.consts.ApplicationConstants.PREF_LOCATION
import com.example.mymoxyktproject.domain.consts.ApplicationConstants.PREF_LONG
import com.example.mymoxyktproject.domain.consts.ApplicationConstants.REQUEST_CODE
import com.example.mymoxyktproject.domain.weatherCodeToImage
import com.example.mymoxyktproject.presentation.presenter.MainPresenter
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class MainActivity : MvpAppCompatActivity(), IMainView {

    @InjectPresenter
    internal lateinit var presenter: MainPresenter
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeRefresh.setOnRefreshListener { requestLocation() }
    }

    override fun setData(data: CurrentWeatherEntity) {
        textViewName.text = resources.getString(
            R.string.name_country,
            data.location.name,
            data.location.country
        )
        textViewObservationTime.text =
            resources.getString(R.string.observation_time, data.current.observationTime)

        textViewCurrent.text = data.current.weatherDescriptions.getOrNull(0)
        textViewTemperature.text =
            resources.getString(R.string.temperature, data.current.temperature)
        textViewPressure.text =
            resources.getString(R.string.pressure, data.current.pressure)
        textViewHumidity.text =
            resources.getString(R.string.humidity, data.current.humidity)
        textViewWindSpeed.text =
            resources.getString(R.string.wind_speed, data.current.windSpeed)
        textViewCloudCover.text =
            resources.getString(R.string.cloud_cover, data.current.cloudCover)
        Glide.with(this).load(weatherCodeToImage(data.current.weatherCode)).centerCrop()
            .into(imageViewWeatherIcon)
    }

    override fun showProgressBar() = with(swipeRefresh) { isRefreshing = true }

    override fun hideProgressBar() = with(swipeRefresh) { isRefreshing = false }

    override fun onError() =
        with(MaterialAlertDialogBuilder(this)) {
            setTitle(R.string.error_title)
            setMessage(R.string.error)
            setPositiveButton(R.string.update) { _: DialogInterface, _: Int -> getCurrentLocation() }
            setCancelable(false)
            create()
        }.show()

    override fun getCurrentLocation() {
        if (!isNetworkConnected())
            onError()
        else if (prefsContainsLocation()) {
            val data = readLocationFromPrefs()
            presenter.loadData(data.first, data.second)
        } else if (isPermissionsGiven())
            requestLocation()
        else
            requestPermissions()
    }

    private fun requestLocation() {
        if (!isLocationEnabled())
            startActivityForResult(
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                REQUEST_CODE
            )
        else
            getLocation()
    }

    private fun isLocationEnabled(): Boolean =
        (getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
            LocationManager.GPS_PROVIDER
        )

    private fun isPermissionsGiven(): Boolean =
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> true
            else -> false
        }

    private fun isNetworkConnected(): Boolean =
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetwork != null

    private fun requestPermissions() = ActivityCompat.requestPermissions(
        this,
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        REQUEST_CODE
    )

    private fun getLocation() {
        val request = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1500
            fastestInterval = 1000
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            p0?.locations?.last()?.let {
                presenter.loadData(it.latitude.toFloat(), it.longitude.toFloat())
            }
            fusedLocationProviderClient.removeLocationUpdates(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) getCurrentLocation()
            else finish()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && isLocationEnabled())
            getLocation()
        else
            hideProgressBar()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun saveDataInPrefs(lat: Float, long: Float) =
        with(getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE).edit()) {
            putFloat(PREF_LAT, lat)
            putFloat(PREF_LONG, long)
            apply()
        }

    private fun readLocationFromPrefs(): Pair<Float, Float> =
        with(getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE)) {
            getFloat(PREF_LAT, 0f) to getFloat(PREF_LONG, 0f)
        }

    private fun prefsContainsLocation(): Boolean =
        getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE).contains(PREF_LAT)
}