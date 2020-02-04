# simpleWeatherAppKt


<img src="https://github.com/IllidanStormrage1/simpleWeatherAppKt/blob/master/Screenshots/photo_2020-02-04_21-51-47_pixel_really_blue_portrait.png" width="430"/> <img src="https://github.com/IllidanStormrage1/simpleWeatherAppKt/blob/master/Screenshots/photo_3_pixel_really_blue_portrait.png" width="430"/> 

> Please do not pay attention to the weather icon, this comes from the bekend 😥

# Overview
Weather app created using the [Weather Api](https://weatherstack.com/quickstart).
This is not supposed to be a production scale application, it is meant to demonstrate the implementation of **MVP** architecture in Kotlin using following libraries:
* Moxy
* Retrofit
* Glide
* Kotlin coroutines

##### Still if you have any issues or suggestions, please feel free an write in telegram [@zkv_2](https://t.me/zkv_2)

### General flow of data
* Retrieve the latitude and longitude of the user.
* Request data from Weather Api
* If data received, cache it in SharedPrefs and show the updated data to user.
* If error then notify user about it.
* To update, pull from top to bottom
