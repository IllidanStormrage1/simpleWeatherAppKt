# simpleWeatherAppKt


<img src="https://github.com/IllidanStormrage1/simpleWeatherAppKt/blob/master/Screenshots/pixel_quite_black_portrait.png" width="287"/> <img src="https://github.com/IllidanStormrage1/simpleWeatherAppKt/blob/master/Screenshots/pixel_quite_black_portrait2.png" width="287"/> <img src="https://github.com/IllidanStormrage1/simpleWeatherAppKt/blob/master/Screenshots/pixel_quite_black_landscape3.png" width="287"/>

# Overview
Weather app created using the [Weather Api](https://weatherstack.com/quickstart).
This is not supposed to be a production scale application, it is meant to demonstrate the implementation of **MVP** architecture in Kotlin using following libraries:
* Moxy
* Retrofit
* Glide
* Kotlin coroutines
 * gms:play-services-location

##### Still if you have any issues or suggestions, please feel free an write in telegram [@zkv_2](https://t.me/zkv_2)

### General flow of data
* Retrieve the latitude and longitude of the user.
* Request data from Weather Api
* If data received, cache it in SharedPrefs and show the updated data to user.
* If error then notify user about it.
* To update, pull from top to bottom
